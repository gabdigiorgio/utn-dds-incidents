package org.utn.presentation.api.url_mappings;

import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import org.utn.presentation.api.controllers.IncidentsController;

public class IncidentsResource implements EndpointGroup {

    @Override
    public void addEndpoints() {
        ApiBuilder.path("/api/incidents", () -> {
            ApiBuilder.get("/", IncidentsController.getIncidents);
            ApiBuilder.get("/{id}", IncidentsController.getIncident);
            ApiBuilder.post("/", IncidentsController.createIncident);
            ApiBuilder.put("/{id}", IncidentsController.editIncident);
            ApiBuilder.delete("/{id}", IncidentsController.deleteIncident);
            ApiBuilder.put("/{id}/estado", IncidentsController.updateIncidentState);
            ApiBuilder.post("/upload_csv", IncidentsController.createMassiveIncident); //upload incidents from csv
        });
    }
}





