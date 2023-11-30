package org.utn.presentation.api.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import org.utn.domain.incident.Incident;
import org.utn.domain.incident.state.State;
import org.utn.domain.job.Job;
import org.utn.modules.ManagerFactory;
import org.utn.presentation.api.dto.*;
import org.utn.presentation.api.dto.requests.CreateIncidentRequest;
import org.utn.presentation.api.dto.requests.EditIncidentRequest;
import org.utn.presentation.api.dto.requests.EmployeeRequest;
import org.utn.presentation.api.dto.requests.RejectedReasonRequest;
import org.utn.presentation.api.dto.responses.ErrorResponse;
import org.utn.presentation.incidents_load.CsvReader;
import org.utn.presentation.worker.MQCLient;
import org.utn.utils.DateUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class IncidentsController {
    private ObjectMapper objectMapper;

    public IncidentsController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Handler getIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer id = getId(ctx);

        Incident incident = incidentManager.getIncident(id);

        returnJson(objectMapper.writeValueAsString(incident), ctx);
    };

    public Handler getIncidents = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();
        String orderBy = ctx.queryParamAsClass("orderBy", String.class).getOrDefault("createdAt");
        String status = ctx.queryParamAsClass("status", String.class).getOrDefault(null);
        String place = ctx.queryParamAsClass("catalogCode", String.class).getOrDefault(null);
        Integer page = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
        Integer pageSize = ctx.queryParamAsClass("pageSize", Integer.class).getOrDefault(10);

        Integer startIndex = (page - 1) * pageSize;

        List<Incident> incidents = incidentManager.getIncidentsWithPagination(startIndex, pageSize, orderBy, status, place);

        returnJson(objectMapper.writeValueAsString(incidents), ctx);
    };

    public Handler createIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        CreateIncidentRequest request = ctx.bodyAsClass(CreateIncidentRequest.class);

        Incident newIncident = incidentManager.createIncident(request.catalogCode,
                DateUtils.parseDate(request.reportDate),
                request.description,
                State.REPORTED.toString(),
                null,
                request.reporterId,
                null,
                null);

        returnJson(objectMapper.writeValueAsString(newIncident), ctx);
        ctx.status(201);
    };

    public Handler editIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer id = getId(ctx);

        EditIncidentRequest request = ctx.bodyAsClass(EditIncidentRequest.class);

        Incident editedIncident = incidentManager.editIncident(id, request);

        returnJson(objectMapper.writeValueAsString(editedIncident), ctx);
    };

    public Handler assignEmployeeIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer id = getId(ctx);

        EmployeeRequest request = ctx.bodyAsClass(EmployeeRequest.class);

        Incident editedIncident = incidentManager.assignEmployeeIncident(id, request.getEmployee());

        returnJson(objectMapper.writeValueAsString(editedIncident), ctx);
    };

    public Handler confirmIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer id = getId(ctx);

        Incident editedIncident = incidentManager.confirmIncident(id);

        returnJson(objectMapper.writeValueAsString(editedIncident), ctx);
    };

    public Handler startProgressIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer id = getId(ctx);

        Incident editedIncident = incidentManager.startProgressIncident(id);

        returnJson(objectMapper.writeValueAsString(editedIncident), ctx);
    };

    public Handler resolveIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer id = getId(ctx);

        Incident editedIncident = incidentManager.resolveIncident(id);

        returnJson(objectMapper.writeValueAsString(editedIncident), ctx);
    };

    public Handler dismissIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer id = getId(ctx);

        RejectedReasonRequest request = ctx.bodyAsClass(RejectedReasonRequest.class);

        Incident editedIncident = incidentManager.dismissIncident(id, request.getRejectedReason());

        returnJson(objectMapper.writeValueAsString(editedIncident), ctx);
    };

    public Handler getInaccessibleAccessibilityFeatures = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer limit = ctx.queryParamAsClass("limit", Integer.class).getOrDefault(10);
        String line = ctx.queryParamAsClass("line", String.class).getOrDefault(null);
        String station = ctx.queryParamAsClass("station", String.class).getOrDefault(null);

        var accessibilityFeatures = incidentManager.getInaccessibleAccessibilityFeatures(limit, line, station);

        ctx.json(accessibilityFeatures);
    };

    private void returnJson(String objectMapper, Context ctx) {
        String json = objectMapper;
        ctx.json(json);
    }
    private static int getId(Context ctx) {
        return Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
    }

    public Handler deleteIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        int id = getId(ctx);

        incidentManager.deleteIncident(id);
        ctx.status(204);
    };

    private boolean areCsvHeadersValid(String csvText) {
        try {
            CSVParser csvParser = new CSVParserBuilder()
                    .withSeparator('\t')
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreQuotations(true)
                    .build();

            CSVReader csvReader = new CSVReaderBuilder(new StringReader(csvText))
                    .withSkipLines(0)
                    .withCSVParser(csvParser)
                    .build();

            String[] headers = csvReader.readNext();
            CsvReader.deleteCharacterBOM(headers);
            validateCsvHeaders(headers);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void validateCsvHeaders(String[] headers) {
        try {
            CsvReader.checkHeaders(headers);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
                if (areCsvHeadersValid(text)) {
                    Job job = ManagerFactory.createJobManager().createJob(text); //TODO: pasar a capa aplicación
                    sendToWorker(job.getId().toString());
                    ctx.json(Map.of("jobId", job.getId().toString()));
                    ctx.status(200);
                } else {
                    ctx.status(400);
                    ctx.json(parseErrorResponse(400, "Los headers del archivo CSV no son válidos."));
                }
            } else {
                ctx.status(400);
                ctx.json("No se recibió ningún archivo.");
            }
        } catch (Exception error) {
            ctx.json(parseErrorResponse(400, error.getMessage()));
            ctx.status(400);
        }
    };

    public Handler getCsvProcessingState = ctx -> {
        Integer id = getId(ctx);
        var jobManager = ManagerFactory.createJobManager();
        var job = jobManager.getJob(id);
        CsvProcessingStateResponse response = new CsvProcessingStateResponse(job.getState(), job.getErrorMessage());
        returnJson(objectMapper.writeValueAsString(response), ctx);

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