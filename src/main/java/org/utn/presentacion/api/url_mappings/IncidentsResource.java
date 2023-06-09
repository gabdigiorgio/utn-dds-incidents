package org.utn.presentacion.api.url_mappings;

import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import org.utn.controllers.IncidentsController;

public class IncidentsResource implements EndpointGroup {

    @Override
    public void addEndpoints() {
        ApiBuilder.path("/incidents", () -> {
            ApiBuilder.get("/", IncidentsController.getIncidents);
            ApiBuilder.post("/", IncidentsController.createIncident);
            ApiBuilder.put("/{id}", IncidentsController.editIncident);
            ApiBuilder.delete("/{id}", IncidentsController.deleteIncident);
            ApiBuilder.put("/{id}/estado", IncidentsController.updateIncidentState);
        });
    }
}





