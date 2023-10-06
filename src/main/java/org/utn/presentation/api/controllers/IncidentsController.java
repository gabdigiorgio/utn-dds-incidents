package org.utn.presentation.api.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import javassist.NotFoundException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.utn.application.IncidentManager;
import org.utn.domain.incident.Incident;
import org.utn.domain.incident.StateEnum;
import org.utn.domain.incident.StateTransitionException;
import org.utn.persistence.DbIncidentsRepository;
import org.utn.presentation.api.inputs.*;
import org.utn.presentation.incidents_load.CsvReader;
import org.utn.presentation.worker.MQCLient;
import org.utn.utils.DateUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class IncidentsController {
    static IncidentManager manager = new IncidentManager(DbIncidentsRepository.getInstance());


    public static Handler getIncidents = ctx -> {
        Integer limit = ctx.queryParamAsClass("limit", Integer.class).getOrDefault(10);
        String orderBy = ctx.queryParamAsClass("orderBy", String.class).getOrDefault("createdAt");
        String status = ctx.queryParamAsClass("status", String.class).getOrDefault(null);
        String place = ctx.queryParamAsClass("place", String.class).getOrDefault(null);

        // get incidents
        List<Incident> incidents = manager.getIncidents(limit, orderBy, status, place);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        String json = objectMapper.writeValueAsString(incidents);
        ctx.json(json);
        ctx.status(200);
    };

    public static Handler createIncident = ctx -> {
        try {
            CreateIncident data = ctx.bodyAsClass(CreateIncident.class);

            // create incident
            Incident newIncident = manager.createIncident(data.catalogCode,
                    DateUtils.parseDate(data.reportDate),
                    data.description,StateEnum.REPORTED.getStateName(),
                    null,
                    data.reporterId,
                    null,
                    null);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            String json = objectMapper.writeValueAsString(newIncident);

            ctx.json(json);
            ctx.status(201);

        } catch (UnrecognizedPropertyException unRecognizedPropertyError) {
            String message = String.format("Campo desconocido: '%s'", unRecognizedPropertyError.getPropertyName());
            ctx.json(parseErrorResponse(400, message));
            ctx.status(400);
        } catch (Exception error) {
            ctx.json(parseErrorResponse(400, error.getMessage()));
            ctx.status(400);
        }
    };

    public static Handler editIncident = ctx -> {
        try {
            Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
            EditIncident data = ctx.bodyAsClass(EditIncident.class);

            // edit incident
            Incident editedIncident = manager.editIncident(id, data);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            String json = objectMapper.writeValueAsString(editedIncident);

            ctx.json(json);
            ctx.status(200);

        } catch (NotFoundException notFoundError) {
            ctx.json(parseErrorResponse(404, notFoundError.getMessage()));
            ctx.status(404);
        } catch (Exception error) {
            ctx.json(parseErrorResponse(400, error.getMessage()));
            ctx.status(400);
        }
    };
    public static Handler updateIncidentState = ctx -> {
        try {
            Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
            ChangeState request = ctx.bodyAsClass(ChangeState.class);

            Incident editedIncident = manager.updateIncidentState(id, request);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            String json = objectMapper.writeValueAsString(editedIncident);
            ctx.result(json).contentType("application/json");

            //ctx.json(objectMapper.writeValueAsString(editedIncident));
            ctx.status(200);

        } catch (StateTransitionException transitionError) {
            ctx.json(parseErrorResponse(422, transitionError.getMessage()));
            ctx.status(422);
        } catch (NotFoundException notFoundError) {
            ctx.json(parseErrorResponse(404, notFoundError.getMessage()));
            ctx.status(404);
        } catch (Exception error) {
            ctx.json(parseErrorResponse(400, error.getMessage()));
            ctx.status(400);
        }
    };

    public static Handler deleteIncident = ctx -> {
        try {
            int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));

            // delete incident
            manager.deleteIncident(id);

            ctx.status(204);

        } catch (NotFoundException notFoundError) {
            ctx.json(parseErrorResponse(404, notFoundError.getMessage()));
            ctx.status(404);
        } catch (Exception error) {
            ctx.json(parseErrorResponse(400, error.getMessage()));
            ctx.status(400);
        }
    };

    private static void sendToWorker(String payload) throws Exception {
        MQCLient mqClient = new MQCLient();
        mqClient.publish(payload);
    }

    public static Handler createMassiveIncident = ctx -> {
        try {
            UploadedFile file = ctx.uploadedFile("file");
            if (file != null) {
                InputStream inputStream = new ByteArrayInputStream(file.content().readAllBytes());
                String text = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

                sendToWorker(text);
                ctx.status(200);
            } else {
                ctx.status(400);
                ctx.json("No se recibió ningún archivo.");
            }

        } catch (Exception error) {
            ctx.json(parseErrorResponse(400, error.getMessage()));
            ctx.status(400);
        }
    };


    public static String parseErrorResponse(int statusCode, String errorMsg) throws JsonProcessingException {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.status = statusCode;
        errorResponse.message = errorMsg;
        errorResponse.errors = Collections.singletonList(errorMsg);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return objectMapper.writeValueAsString(errorResponse);
    }

}