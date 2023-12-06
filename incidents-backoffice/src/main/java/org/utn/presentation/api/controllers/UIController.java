package org.utn.presentation.api.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;
import javassist.NotFoundException;
import org.utn.domain.accessibility_feature.AccessibilityFeature;
import org.utn.domain.incident.Incident;
import org.utn.modules.ManagerFactory;
import org.utn.presentation.api.dto.responses.AccessibilityFeatureResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import static org.utn.presentation.api.controllers.IncidentsController.parseErrorResponse;

public class UIController {

    public UIController() {
    }

    public Handler getLogin = ctx -> {
        try {
            ctx.render("login.hbs");
        } catch (Exception error) {
            ctx.json(parseErrorResponse(500, error.getMessage()));
            ctx.status(500);
            ctx.render("error.hbs");
        }
    };

    public Handler getRegisterUser = ctx -> {
        try {
            ctx.render("register_user.hbs");
        } catch (Exception error) {
            ctx.json(parseErrorResponse(500, error.getMessage()));
            ctx.status(500);
            ctx.render("error.hbs");
        }
    };

    public Handler getRegisterOperator = ctx -> {
        try {
            ctx.render("register_operator.hbs");
        } catch (Exception error) {
            ctx.json(parseErrorResponse(500, error.getMessage()));
            ctx.status(500);
            ctx.render("error.hbs");
        }
    };

    public Handler getIncidents = ctx -> {
        try {
            var incidentManager = ManagerFactory.createIncidentManager();
            Map<String, Object> model = new HashMap<>();

            Integer pageSize = 10;

            int totalIncidents = incidentManager.getTotalIncidentsCount();
            int totalPages = (int) Math.ceil((double) totalIncidents / pageSize);

            model.put("pageSize", pageSize);
            model.put("totalPages", totalPages);

            ctx.render("incidents.hbs", model);
        } catch (Exception error) {
            ctx.json(parseErrorResponse(500, error.getMessage()));
            ctx.status(500);
            ctx.render("error.hbs");
        }
    };


    public Handler getInaccessibleAccessibilityFeatures = ctx -> {
        try {
            var incidentManager = ManagerFactory.createIncidentManager();
            Map<String, Object> model = new HashMap<>();
            var inaccessibleAccessibilityFeatures = incidentManager.getAccessibilityFeatures(null,
                    "inaccessible", null, null);

            var accessibilityFeaturesResponse = inaccessibleAccessibilityFeatures.stream().
                    map(this::mapToAccessibilityFeatureResponse).toList();

            model.put("inaccessibleAccessibilityFeatures", accessibilityFeaturesResponse);
            ctx.render("inaccessible_accessibility_features.hbs", model);
        } catch (Exception error) {
            ctx.json(parseErrorResponse(400, error.getMessage()));
            ctx.status(400);
        }
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

    public Handler getIncident = ctx -> {
        try {
            var incidentManager = ManagerFactory.createIncidentManager();
            Map<String, Object> model = new HashMap<>();
            Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));

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
            var incidentManager = ManagerFactory.createIncidentManager();
            Map<String, Object> model = new HashMap<>();
            Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));

            Incident incident = incidentManager.getIncident(id);
            model.put("incident", incident);
            ctx.render("edit_incident.hbs", model);
        } catch (NotFoundException notFoundError) {
            ctx.status(404);
            ctx.result("Incidencia no encontrada");
        } catch (Exception error) {
            ctx.json(parseErrorResponse(400, error.getMessage()));
            ctx.status(400);
        }
    };

    public Handler updateIncidentState = ctx -> {
        try {
            var incidentManager = ManagerFactory.createIncidentManager();
            Map<String, Object> model = new HashMap<>();
            Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
            Incident incident = incidentManager.getIncident(id);
            model.put("incident", incident);
            ctx.render("edit_incident_state.hbs", model);
        } catch (NotFoundException notFoundError) {
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

