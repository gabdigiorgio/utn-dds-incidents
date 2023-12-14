package org.utn.presentation.api.url_mappings;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import org.utn.domain.users.Role;
import org.utn.presentation.api.controllers.IncidentsController;

public class IncidentsResource implements EndpointGroup {
    ObjectMapper objectMapper;

    public IncidentsResource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void addEndpoints() {
        IncidentsController incidentsController = new IncidentsController(objectMapper);
        ApiBuilder.path("/api/incidents", () -> {
            ApiBuilder.get("/accessibility-features", incidentsController.getAccessibilityFeatures, Role.ANYONE);
            ApiBuilder.get("/lines", incidentsController.getLines, Role.ANYONE);
            ApiBuilder.get("/lines/{id}/stations", incidentsController.getStationsFromLine, Role.ANYONE);
            ApiBuilder.get("/", incidentsController.getIncidents, Role.ANYONE);
            ApiBuilder.get("/{id}", incidentsController.getIncident, Role.ANYONE);
            ApiBuilder.post("/", incidentsController.createIncident, Role.USER);
            ApiBuilder.put("/{id}", incidentsController.editIncident, Role.USER, Role.OPERATOR);
            ApiBuilder.delete("/{id}", incidentsController.deleteIncident, Role.OPERATOR);
            ApiBuilder.post("/{id}/assign-employee", incidentsController.assignEmployeeIncident, Role.OPERATOR);
            ApiBuilder.post("/{id}/confirm", incidentsController.confirmIncident, Role.OPERATOR);
            ApiBuilder.post("/{id}/start-progress", incidentsController.startProgressIncident, Role.OPERATOR);
            ApiBuilder.post("/{id}/resolve", incidentsController.resolveIncident, Role.OPERATOR);
            ApiBuilder.post("/{id}/dismiss", incidentsController.dismissIncident, Role.OPERATOR);
            ApiBuilder.post("/upload-csv", incidentsController.createMassiveIncident, Role.OPERATOR);
            ApiBuilder.get("/processing-csv-state/{id}", incidentsController.getCsvProcessingState, Role.OPERATOR);
        });
    }
}





