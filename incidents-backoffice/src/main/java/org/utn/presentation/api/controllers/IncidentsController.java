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
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import org.utn.domain.incident.Incident;
import org.utn.domain.incident.State;
import org.utn.domain.job.Job;
import org.utn.modules.ManagerFactory;
import org.utn.presentation.api.dto.*;
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

    public Handler getIncidents = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();
        String orderBy = ctx.queryParamAsClass("orderBy", String.class).getOrDefault("createdAt");
        String status = ctx.queryParamAsClass("status", String.class).getOrDefault(null);
        String place = ctx.queryParamAsClass("catalog_code", String.class).getOrDefault(null);
        Integer page = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
        Integer pageSize = ctx.queryParamAsClass("page_size", Integer.class).getOrDefault(10);

        Integer startIndex = (page - 1) * pageSize;

        List<Incident> incidents = incidentManager.getIncidentsWithPagination(startIndex, pageSize, orderBy, status, place);

        String json = objectMapper.writeValueAsString(incidents);
        ctx.json(json);
    };

    public Handler getInaccessibleAccessibilityFeatures = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();
        Integer limit = ctx.queryParamAsClass("limit", Integer.class).getOrDefault(10);
        String line = ctx.queryParamAsClass("line", String.class).getOrDefault(null);
        String station = ctx.queryParamAsClass("station", String.class).getOrDefault(null);

        var accessibilityFeatures = incidentManager.getInaccessibleAccessibilityFeatures(limit, line, station);

        ctx.json(accessibilityFeatures);
    };

    public Handler getIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();
        Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));

        Incident incident = incidentManager.getIncident(id);

        String json = objectMapper.writeValueAsString(incident);
        ctx.json(json);
    };

    public Handler createIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        CreateIncident data = ctx.bodyAsClass(CreateIncident.class);

        Incident newIncident = incidentManager.createIncident(data.catalogCode,
                DateUtils.parseDate(data.reportDate),
                data.description,
                State.REPORTED.toString(),
                null,
                data.reporterId,
                null,
                null);

        String json = objectMapper.writeValueAsString(newIncident);

        ctx.json(json);
        ctx.status(201);
    };

    public Handler editIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
        EditIncidentRequest request = ctx.bodyAsClass(EditIncidentRequest.class);

        Incident editedIncident = incidentManager.editIncident(id, request);

        String json = objectMapper.writeValueAsString(editedIncident);

        ctx.json(json);
    };

    public Handler assignEmployeeIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));

        EmployeeRequest request = ctx.bodyAsClass(EmployeeRequest.class);

        Incident editedIncident = incidentManager.assignEmployeeIncident(id, request.getEmployee());

        String json = objectMapper.writeValueAsString(editedIncident);
        ctx.result(json);
    };

    public Handler confirmIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));

        Incident editedIncident = incidentManager.confirmIncident(id);

        String json = objectMapper.writeValueAsString(editedIncident);
        ctx.result(json);
    };

    public Handler startProgressIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));

        Incident editedIncident = incidentManager.startProgressIncident(id);

        String json = objectMapper.writeValueAsString(editedIncident);
        ctx.result(json);
    };

    public Handler resolveIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));

        Incident editedIncident = incidentManager.resolveIncident(id);

        String json = objectMapper.writeValueAsString(editedIncident);
        ctx.result(json);
    };

    public Handler dismissIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));

        RejectedReasonRequest request = ctx.bodyAsClass(RejectedReasonRequest.class);

        Incident editedIncident = incidentManager.dismissIncident(id, request.getRejectedReason());

        String json = objectMapper.writeValueAsString(editedIncident);
        ctx.result(json);
    };

    public Handler deleteIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));

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
        Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
        var jobManager = ManagerFactory.createJobManager();
        var job = jobManager.getJob(id);
        CsvProcessingStateResponse response = new CsvProcessingStateResponse(job.getState(), job.getErrorMessage());
        String jsonResponse = objectMapper.writeValueAsString(response);
        ctx.json(jsonResponse);

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