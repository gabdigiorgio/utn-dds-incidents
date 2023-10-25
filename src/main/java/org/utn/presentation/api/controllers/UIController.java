package org.utn.presentation.api.controllers;

import io.javalin.http.Handler;
import javassist.NotFoundException;
import org.utn.application.IncidentManager;
import org.utn.domain.incident.Incident;
import org.utn.persistence.DbIncidentsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.utn.presentation.api.controllers.IncidentsController.parseErrorResponse;

public class UIController {
    static IncidentManager manager = new IncidentManager(DbIncidentsRepository.getInstance());


    public static Handler getIncidents = ctx -> {
        try {

            Map<String, Object> model = new HashMap<>();

            // get incidents

            List<Incident> incidents = manager.getIncidents(1000, "createdAt", null, null);
            model.put("incidents", incidents);
            ctx.render("incidents.hbs", model);


        } catch (Exception error) {
            ctx.json(parseErrorResponse(500, error.getMessage()));
            ctx.status(500);
            ctx.render("error.hbs");

        }
    };

    public static Handler getIncident = ctx -> {
        try {
            Map<String, Object> model = new HashMap<>();
            Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));

            // get incident by id
            Incident incident = manager.getIncident(id);
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

    public static Handler createIncident = ctx -> {
        try {
            ctx.render("create_incident.hbs");
        } catch (Exception error) {
            ctx.json(parseErrorResponse(400, error.getMessage()));
            ctx.status(400);
        }
    };

    public static Handler editIncident = ctx -> {
        try {
            Map<String, Object> model = new HashMap<>();
            Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));

            // get incident by id
            Incident incident = manager.getIncident(id);
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

    public static Handler createMassiveIncident = ctx -> {
        try {
            ctx.render("incident_upload_csv.hbs");
        } catch (Exception error) {
            ctx.status(500);
            ctx.html("Error al procesar el archivo CSV: " + error.getMessage());
        }
    };

}

