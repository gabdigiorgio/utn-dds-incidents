package org.utn.presentation.api.url_mappings;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import org.utn.presentation.api.controllers.IncidentsController;
import org.utn.domain.users.Role;

public class IncidentsResource implements EndpointGroup {
    ObjectMapper objectMapper;

    public IncidentsResource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void addEndpoints() {
        IncidentsController incidentsController = new IncidentsController(objectMapper);
        ApiBuilder.path("/api/incidents", () -> {
            ApiBuilder.get("/inaccessible-accessibility-features", incidentsController.getInaccessibleAccessibilityFeatures);
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
            ApiBuilder.post("/upload-csv", incidentsController.createMassiveIncident);
            ApiBuilder.get("/processing-csv-state/{id}", incidentsController.getCsvProcessingState);
        });
    }
}





