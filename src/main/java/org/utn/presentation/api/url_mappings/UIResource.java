package org.utn.presentation.api.url_mappings;

import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import org.utn.presentation.api.controllers.UIController;

public class UIResource implements EndpointGroup {
    @Override
    public void addEndpoints() {
        ApiBuilder.path("/ui/incidents", () -> {
            //ApiBuilder.get("/", UIController.getIncidents);
            ApiBuilder.get("/{id}", UIController.getIncident);
            //ApiBuilder.post("/", UIController.createIncident);
            //ApiBuilder.put("/{id}", UIController.editIncident);
            //ApiBuilder.put("/{id}/estado", UIController.updateIncidentState);
            //ApiBuilder.post("/upload_csv", UIController.createMassiveIncident);
        });
    }
}





