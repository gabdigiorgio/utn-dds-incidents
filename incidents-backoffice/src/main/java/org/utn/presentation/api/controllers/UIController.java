package org.utn.presentation.api.controllers;

import io.javalin.http.Handler;
import javassist.NotFoundException;
import org.utn.application.IncidentManager;
import org.utn.application.JobManager;
import org.utn.domain.incident.Incident;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.utn.presentation.api.controllers.IncidentsController.parseErrorResponse;

public class UIController {
    private IncidentManager incidentManager;
    private JobManager jobManager;

    public UIController(IncidentManager incidentManager, JobManager jobManager) {
        this.incidentManager = incidentManager;
        this.jobManager = jobManager;
    }

    public Handler getIncidents = ctx -> {
        try {

            Map<String, Object> model = new HashMap<>();

            // get incidents

            List<Incident> incidents = incidentManager.getIncidents(10, "createdAt", null, null);
            model.put("incidents", incidents);
            ctx.render("incidents.hbs", model);


        } catch (Exception error) {
            ctx.json(parseErrorResponse(400, error.getMessage()));
            ctx.status(400);
        }
    };

    public Handler getIncident = ctx -> {
        try {
            Map<String, Object> model = new HashMap<>();
            Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));

            // get incident by id
            Incident incident = incidentManager.getIncident(id);
            model.put("incident", incident);
            ctx.render("incident.hbs", model);

        } catch (NotFoundException notFoundError) {
            ctx.status(404);
            ctx.result("Incidencia no encontrada");
        } catch (Exception error) {
            ctx.json(parseErrorResponse(400, error.getMessage()));
            ctx.status(400);
        }
    };

    public Handler createIncident = ctx -> {
        try {
            ctx.render("create_incident.hbs");
        } catch (Exception error) {
            ctx.json(parseErrorResponse(400, error.getMessage()));
            ctx.status(400);
        }
    };

    public Handler editIncident = ctx -> {
        try {
            Map<String, Object> model = new HashMap<>();
            Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));

            Incident incident = incidentManager.getIncident(id);
            model.put("incident", incident);
            ctx.render("edit_incident.hbs", model);
        }  catch (NotFoundException notFoundError) {
            ctx.status(404);
            ctx.result("Incidencia no encontrada");
        } catch (Exception error) {
            ctx.json(parseErrorResponse(400, error.getMessage()));
            ctx.status(400);
        }
    };

    public Handler updateIncidentState = ctx -> {
        try {
            Map<String, Object> model = new HashMap<>();
            Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
            Incident incident = incidentManager.getIncident(id);
            model.put("incident", incident);
            ctx.render("edit_incident_state.hbs", model);
        }  catch (NotFoundException notFoundError) {
            ctx.status(404);
            ctx.result("Incidencia no encontrada");
        } catch (Exception error) {
            ctx.json(parseErrorResponse(400, error.getMessage()));
            ctx.status(400);
        }
    };

    public Handler createMassiveIncident = ctx -> {
        try {
            ctx.render("incident_upload_csv.hbs");
        } catch (Exception error) {
            ctx.status(500);
            ctx.html("Error al procesar el archivo CSV: " + error.getMessage());
        }
    };

    public Handler getCsvProcessingState = ctx -> {
        try {
            Map<String, Object> model = new HashMap<>();
            Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));

            model.put("jobId", id.toString());
            ctx.render("incident_processing_csv_state.hbs", model);
        } catch (Exception error) {
            ctx.status(500);
            ctx.html("Job no encontrado: " + error.getMessage());
        }
    };

}

