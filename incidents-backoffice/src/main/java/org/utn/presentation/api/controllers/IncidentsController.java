package org.utn.presentation.api.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import javassist.NotFoundException;
import org.utn.application.IncidentManager;
import org.utn.application.JobManager;
import org.utn.domain.incident.Incident;
import org.utn.domain.incident.InventoryService;
import org.utn.domain.incident.StateEnum;
import org.utn.domain.incident.StateTransitionException;
import org.utn.domain.job.Job;
import org.utn.domain.job.ProcessState;
import org.utn.presentation.api.dto.*;
import org.utn.presentation.incidents_load.CsvReader;
import org.utn.presentation.worker.MQCLient;
import org.utn.utils.DateUtils;
import org.utn.utils.exceptions.validator.InvalidCatalogCodeException;
import org.utn.utils.exceptions.validator.InvalidDateException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class IncidentsController {
    private IncidentManager incidentManager;
    private JobManager jobManager;
    private ObjectMapper objectMapper;

    public IncidentsController(IncidentManager incidentManager, JobManager jobManager, ObjectMapper objectMapper) {
        this.incidentManager = incidentManager;
        this.jobManager = jobManager;
        this.objectMapper = objectMapper;
    }

    public Handler getIncidents = ctx -> {
        String orderBy = ctx.queryParamAsClass("orderBy", String.class).getOrDefault("createdAt");
        String status = ctx.queryParamAsClass("status", String.class).getOrDefault(null);
        String place = ctx.queryParamAsClass("place", String.class).getOrDefault(null);
        Integer page = ctx.queryParamAsClass("page", Integer.class).getOrDefault(1);
        Integer pageSize = ctx.queryParamAsClass("page_size", Integer.class).getOrDefault(10);

        Integer startIndex = (page - 1) * pageSize;
        
        List<Incident> incidents = incidentManager.getIncidentsWithPagination(startIndex, pageSize, orderBy, status, place);

        String json = objectMapper.writeValueAsString(incidents);
        ctx.json(json);
        ctx.status(200);
    };

    public Handler getInaccessibleAccessibilityFeatures = ctx -> {
        Integer limit = ctx.queryParamAsClass("limit", Integer.class).getOrDefault(10);
        String line = ctx.queryParamAsClass("line", String.class).getOrDefault(null);
        String station = ctx.queryParamAsClass("station", String.class).getOrDefault(null);

        var accessibilityFeatures = incidentManager.getInaccessibleAccessibilityFeatures(limit, line, station);

        ctx.json(accessibilityFeatures);
        ctx.status(200);
    };

    public Handler getIncident = ctx -> {
        try {
            Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));

            Incident incident = incidentManager.getIncident(id);

            String json = objectMapper.writeValueAsString(incident);
            ctx.json(json);
            ctx.status(200);
        } catch (NotFoundException notFoundError) {
            handleNotFoundException(ctx, notFoundError);
        } catch (Exception error) {
            handleInternalError(ctx, error);
        }
    };

    public Handler createIncident = ctx -> {

        CreateIncident data = ctx.bodyAsClass(CreateIncident.class);

        // create incident
        Incident newIncident = incidentManager.createIncident(data.catalogCode,
                DateUtils.parseDate(data.reportDate),
                data.description,
                StateEnum.REPORTED.getStateName(),
                null,
                data.reporterId,
                null,
                null);

        String json = objectMapper.writeValueAsString(newIncident);

        ctx.json(json);
        ctx.status(201);
    };

    public Handler editIncident = ctx -> {
        try {
            Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
            EditIncident data = ctx.bodyAsClass(EditIncident.class);

            // edit incident
            Incident editedIncident = incidentManager.editIncident(id, data);

            String json = objectMapper.writeValueAsString(editedIncident);

            ctx.json(json);
            ctx.status(200);

        }
        catch (NotFoundException notFoundError) {
            handleNotFoundException(ctx, notFoundError);
        } catch (Exception error) {
            handleInternalError(ctx, error);
        }
    };

    public Handler updateIncidentState = ctx -> {
        try {
            Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
            ChangeState request = ctx.bodyAsClass(ChangeState.class);

            Incident editedIncident = incidentManager.updateIncidentState(id, request);

            String json = objectMapper.writeValueAsString(editedIncident);
            ctx.result(json).contentType("application/json");
            ctx.status(200);

        } catch (StateTransitionException transitionError) {
            handleBadRequest(ctx, transitionError);
        } catch (IllegalArgumentException illegalArgumentError) {
            handleBadRequest(ctx, illegalArgumentError);
        }
        catch (NotFoundException notFoundError) {
            handleNotFoundException(ctx, notFoundError);
        } catch (Exception error) {
            handleInternalError(ctx, error);
        }
    };

    public Handler deleteIncident = ctx -> {
        try {
            int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));

            // delete incident
            incidentManager.deleteIncident(id);

            ctx.status(204);

        } catch (NotFoundException notFoundError) {
            ctx.json(parseErrorResponse(404, notFoundError.getMessage()));
            ctx.status(404);
        } catch (Exception error) {
            ctx.json(parseErrorResponse(400, error.getMessage()));
            ctx.status(400);
        }
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
                    Job job = jobManager.createJob(text); //TODO: pasar a capa aplicación
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
        try {
            Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));

            ProcessState jobState = jobManager.getJobState(id);
            String jobErrorMessage = jobManager.getJobErrorMessage(id);

            CsvProcessingStateResponse response = new CsvProcessingStateResponse(jobState, jobErrorMessage);
            String jsonResponse = objectMapper.writeValueAsString(response);

            ctx.status(200).json(jsonResponse);
        } catch (Exception error) {
            ctx.json(parseErrorResponse(400, error.getMessage()));
            ctx.status(400);
        }
    };

    private void handleBadRequest(Context ctx,  IllegalArgumentException e) throws JsonProcessingException {
        String message = String.format(e.getMessage());
        ctx.json(parseErrorResponse(400, message));
        ctx.status(400);
    }

    private void handleBadRequest(Context ctx,  StateTransitionException e) throws JsonProcessingException {
        String message = String.format(e.getMessage());
        ctx.json(parseErrorResponse(400, message));
        ctx.status(400);
    }

    private void handleBadRequest(Context ctx, UnrecognizedPropertyException e) throws JsonProcessingException {
        String message = String.format("Campo desconocido: '%s'", e.getPropertyName());
        ctx.json(parseErrorResponse(400, message));
        ctx.status(400);
    }

    private void handleBadRequest(Context ctx,  InvalidDateException e) throws JsonProcessingException {
        ctx.json(parseErrorResponse(400, e.getMessage()));
        ctx.status(400);
    }

    private void handleBadRequest(Context ctx,  InvalidCatalogCodeException e) throws JsonProcessingException {
        ctx.json(parseErrorResponse(400, e.getMessage()));
        ctx.status(400);
    }

    private void handleNotFoundException(Context ctx, NotFoundException notFoundError) throws JsonProcessingException {
        ctx.json(parseErrorResponse(404, notFoundError.getMessage()));
        ctx.status(404);
    }

    private void handleInternalError(Context ctx, Exception e) throws JsonProcessingException {
        ctx.json(parseErrorResponse(500, e.getMessage()));
        ctx.status(500);
    }

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