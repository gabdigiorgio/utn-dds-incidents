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
import org.utn.domain.accessibility_feature.AccessibilityFeature;
import org.utn.domain.accessibility_feature.Line;
import org.utn.domain.accessibility_feature.Station;
import org.utn.domain.incident.Incident;
import org.utn.domain.incident.state.State;
import org.utn.domain.job.Job;
import org.utn.domain.users.User;
import org.utn.modules.ManagerFactory;
import org.utn.modules.RepositoryFactory;
import org.utn.presentation.api.dto.responses.CsvProcessingStateResponse;
import org.utn.presentation.api.dto.requests.CreateIncidentRequest;
import org.utn.presentation.api.dto.requests.EditIncidentRequest;
import org.utn.presentation.api.dto.requests.EmployeeRequest;
import org.utn.presentation.api.dto.requests.RejectedReasonRequest;
import org.utn.presentation.api.dto.responses.*;
import org.utn.presentation.incidents_load.CsvReader;
import org.utn.presentation.worker.MQCLient;
import org.utn.utils.DateUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class IncidentsController {
    private ObjectMapper objectMapper;

    public IncidentsController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Handler getIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer id = getId(ctx);

        Incident incident = incidentManager.getIncident(id);

        IncidentResponse incidentResponse = new IncidentResponse(incident);

        returnJson(objectMapper.writeValueAsString(incidentResponse), ctx);
    };

    public Handler getIncidents = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();
        String orderBy = ctx.queryParamAsClass("orderBy", String.class).getOrDefault("createdAt");
        String stateString = ctx.queryParamAsClass("state", String.class).getOrDefault(null);
        String place = ctx.queryParamAsClass("catalogCode", String.class).getOrDefault(null);
        Integer page = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
        Integer pageSize = ctx.queryParamAsClass("pageSize", Integer.class).getOrDefault(10);

        Integer startIndex = (page - 1) * pageSize;

        State stateEnum = null;
        if (stateString != null) {
            stateEnum = State.valueOf(stateString.toUpperCase());
        }

        List<Incident> incidents = incidentManager.getIncidentsWithPagination(startIndex, pageSize, orderBy, stateEnum, place);

        List<IncidentResponse> incidentResponses = incidents.stream().map(IncidentResponse::new).toList();

        returnJson(objectMapper.writeValueAsString(incidentResponses), ctx);
    };

    public Handler createIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();
        var userRepository = RepositoryFactory.createUserRepository();

        CreateIncidentRequest request = ctx.bodyAsClass(CreateIncidentRequest.class);
        User reporter = userRepository.getByToken(ctx.header("token"));

        Incident newIncident = incidentManager.createIncident(request.catalogCode, DateUtils.parseDate(request.reportDate),
                request.description, State.REPORTED, null, reporter, null, "");

        IncidentResponse incidentResponse = new IncidentResponse(newIncident);

        returnJson(objectMapper.writeValueAsString(incidentResponse), ctx);
        ctx.status(201);
    };

    public Handler editIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer id = getId(ctx);

        EditIncidentRequest request = ctx.bodyAsClass(EditIncidentRequest.class);

        Incident editedIncident = incidentManager.editIncident(id, request);

        IncidentResponse incidentResponse = new IncidentResponse(editedIncident);

        returnJson(objectMapper.writeValueAsString(incidentResponse), ctx);
    };

    public Handler assignEmployeeIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();
        var userRepository = RepositoryFactory.createUserRepository();

        Integer id = getId(ctx);

        EmployeeRequest request = ctx.bodyAsClass(EmployeeRequest.class);
        User operator = userRepository.getByToken(ctx.header("token"));

        incidentManager.setOperator(id, operator);
        Incident editedIncident = incidentManager.assignEmployeeIncident(id, request.getEmployee());

        IncidentResponse incidentResponse = new IncidentResponse(editedIncident);

        returnJson(objectMapper.writeValueAsString(incidentResponse), ctx);
    };

    public Handler confirmIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer id = getId(ctx);

        Incident editedIncident = incidentManager.confirmIncident(id);

        IncidentResponse incidentResponse = new IncidentResponse(editedIncident);

        returnJson(objectMapper.writeValueAsString(incidentResponse), ctx);
    };

    public Handler startProgressIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer id = getId(ctx);

        Incident editedIncident = incidentManager.startProgressIncident(id);

        IncidentResponse incidentResponse = new IncidentResponse(editedIncident);

        returnJson(objectMapper.writeValueAsString(incidentResponse), ctx);
    };

    public Handler resolveIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer id = getId(ctx);

        Incident editedIncident = incidentManager.resolveIncident(id);

        IncidentResponse incidentResponse = new IncidentResponse(editedIncident);

        returnJson(objectMapper.writeValueAsString(incidentResponse), ctx);
    };

    public Handler dismissIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer id = getId(ctx);

        RejectedReasonRequest request = ctx.bodyAsClass(RejectedReasonRequest.class);

        Incident editedIncident = incidentManager.dismissIncident(id, request.getRejectedReason());

        IncidentResponse incidentResponse = new IncidentResponse(editedIncident);

        returnJson(objectMapper.writeValueAsString(incidentResponse), ctx);
    };

    public Handler getAccessibilityFeatures = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        Integer limit = ctx.queryParamAsClass("limit", Integer.class).getOrDefault(null);
        String status = ctx.queryParamAsClass("status", String.class).getOrDefault(null);
        String line = ctx.queryParamAsClass("line", String.class).getOrDefault(null);
        String station = ctx.queryParamAsClass("station", String.class).getOrDefault(null);

        var accessibilityFeatures = incidentManager.getAccessibilityFeatures(limit, status, line, station);
        var accessibilityFeaturesResponse = accessibilityFeatures.stream().map(this::mapToAccessibilityFeatureResponse).toList();

        ctx.json(accessibilityFeaturesResponse);
    };

    public Handler getLines = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();
        var lines = incidentManager.getLines();
        var linesResponse = lines.stream().map(this::mapToLineResponse).toList();

        ctx.json(linesResponse);
    };

    public Handler getStationsFromLine = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();
        var id = getStringId(ctx);
        var stations = incidentManager.getStationsFromLine(id);
        var stationResponses = stations.stream().map(this::mapToStationResponse).toList();

        ctx.json(stationResponses);
    };

    private AccessibilityFeatureResponse mapToAccessibilityFeatureResponse(AccessibilityFeature feature) {
        AccessibilityFeatureResponse response = new AccessibilityFeatureResponse();
        response.setCatalogCode(feature.getCatalogCode());
        response.setType(feature.getType());
        response.setStatus(feature.getStatus());
        response.setStation(feature.getStation());
        response.setLine(feature.getLine());
        return response;
    }

    private LineResponse mapToLineResponse(Line line) {
        LineResponse response = new LineResponse();
        response.setId(line.getId());
        response.setName(line.getName());
        return response;
    }

    private StationResponse mapToStationResponse(Station station) {
        StationResponse response = new StationResponse();
        response.setId(station.getId());
        response.setName(station.getName());
        return response;
    }

    private void returnJson(String objectMapper, Context ctx) {
        String json = objectMapper;
        ctx.json(json);
    }

    private static int getId(Context ctx) {
        return Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
    }

    private static String getStringId(Context ctx) {
        return ctx.pathParam("id");
    }

    public Handler deleteIncident = ctx -> {
        var incidentManager = ManagerFactory.createIncidentManager();

        int id = getId(ctx);

        incidentManager.deleteIncident(id);
        ctx.status(204);
    };

    private boolean areCsvHeadersValid(String csvText) {
        try {
            CSVParser csvParser = new CSVParserBuilder().withSeparator('\t').withIgnoreLeadingWhiteSpace(true).withIgnoreQuotations(true).build();

            CSVReader csvReader = new CSVReaderBuilder(new StringReader(csvText)).withSkipLines(0).withCSVParser(csvParser).build();

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
                    var userRepository = RepositoryFactory.createUserRepository();
                    User creator = userRepository.getByToken(ctx.header("token"));
                    Job job = ManagerFactory.createJobManager().createJob(text, creator); //TODO: pasar a capa aplicación
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