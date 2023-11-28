package org.utn.presentation.api.url_mappings;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import org.utn.application.IncidentManager;
import org.utn.application.JobManager;
import org.utn.presentation.api.controllers.IncidentsController;

public class IncidentsResource implements EndpointGroup {
    JobManager jobManager;
    ObjectMapper objectMapper;

    public IncidentsResource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void addEndpoints() {
        IncidentsController incidentsController = new IncidentsController(objectMapper);
        ApiBuilder.path("/api/incidents", () -> {
            ApiBuilder.get("/inaccessible_accessibility_features", incidentsController.getInaccessibleAccessibilityFeatures);
            ApiBuilder.get("/", incidentsController.getIncidents);
            ApiBuilder.get("/{id}", incidentsController.getIncident);
            ApiBuilder.post("/", incidentsController.createIncident);
            ApiBuilder.put("/{id}", incidentsController.editIncident);
            ApiBuilder.delete("/{id}", incidentsController.deleteIncident);
            ApiBuilder.post("/{id}/assign-employee", incidentsController.assignEmployeeIncident);
            ApiBuilder.post("/{id}/confirm", incidentsController.confirmIncident);
            ApiBuilder.post("/{id}/start-progress", incidentsController.startProgressIncident);
            ApiBuilder.post("/{id}/resolve", incidentsController.resolveIncident);
            ApiBuilder.post("/{id}/dismiss", incidentsController.dismissIncident);
            ApiBuilder.put("/{id}/state", incidentsController.updateIncidentState);
            ApiBuilder.post("/upload_csv", incidentsController.createMassiveIncident);
            ApiBuilder.get("/processing_csv_state/{id}", incidentsController.getCsvProcessingState);
        });
    }
}





