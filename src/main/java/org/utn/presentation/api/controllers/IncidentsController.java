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
import org.utn.application.IncidentManager;
import org.utn.domain.incident.Incident;
import org.utn.domain.incident.StateEnum;
import org.utn.domain.incident.StateTransitionException;
import org.utn.persistence.DbIncidentsRepository;
import org.utn.presentation.api.inputs.ChangeState;
import org.utn.presentation.api.inputs.CreateIncident;
import org.utn.presentation.api.inputs.EditIncident;
import org.utn.presentation.api.inputs.ErrorResponse;
import org.utn.presentation.worker.MQCLient;
import org.utn.utils.DateUtils;
import javax.persistence.EntityManagerFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class IncidentsController {
    private IncidentManager manager;
    private ObjectMapper objectMapper;

    public IncidentsController(EntityManagerFactory entityManagerFactory) {
        this.manager = new IncidentManager(new DbIncidentsRepository(entityManagerFactory.createEntityManager()));
        this.objectMapper = createObjectMapper();
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    public Handler getIncidents = ctx -> {
        Integer limit = ctx.queryParamAsClass("limit", Integer.class).getOrDefault(10);
        String orderBy = ctx.queryParamAsClass("orderBy", String.class).getOrDefault("createdAt");
        String status = ctx.queryParamAsClass("status", String.class).getOrDefault(null);
        String place = ctx.queryParamAsClass("place", String.class).getOrDefault(null);
        Integer from = ctx.queryParamAsClass("from", Integer.class).getOrDefault(0);

        // get incidents
        List<Incident> incidents = manager.getIncidents(limit, orderBy, status, place);

        String json = objectMapper.writeValueAsString(incidents);
        ctx.json(json);
        ctx.status(200);
    };

    public Handler getIncident = ctx -> {
        Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));

        // get incident
        Incident incidents = manager.getIncident(id);

        String json = objectMapper.writeValueAsString(incidents);
        ctx.json(json);
        ctx.status(200);
    };

    public Handler createIncident = ctx -> {
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

    public Handler editIncident = ctx -> {
        try {
            Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
            EditIncident data = ctx.bodyAsClass(EditIncident.class);

            // edit incident
            Incident editedIncident = manager.editIncident(id, data);

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
    public Handler updateIncidentState = ctx -> {
        try {
            Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
            ChangeState request = ctx.bodyAsClass(ChangeState.class);

            Incident editedIncident = manager.updateIncidentState(id, request);

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

    public Handler deleteIncident = ctx -> {
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

    public Handler createMassiveIncident = ctx -> {
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
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.status = statusCode;
        errorResponse.message = errorMsg;
        errorResponse.errors = Collections.singletonList(errorMsg);

        return objectMapper.writeValueAsString(errorResponse);
    }

}