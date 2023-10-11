package org.utn.presentation.api.url_mappings;

import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import org.utn.presentation.api.controllers.UIController;

public class UIResource implements EndpointGroup {
    @Override
    public void addEndpoints() {

        // con esto podríamos generar un home y otras cositas, a evaluar si corresponde o no hacer esto
        ApiBuilder.path("/", () -> {
            ApiBuilder.get("/", ctx -> {
                ctx.render("index.hbs");
            });
        });

        ApiBuilder.path("/ui/incidents", () -> {
            //ApiBuilder.get("/", UIController.getIncidents);
            ApiBuilder.get("/upload_csv", UIController.createMassiveIncident);
            ApiBuilder.get("/{id}", UIController.getIncident);
            //ApiBuilder.post("/", UIController.createIncident);
            //ApiBuilder.put("/{id}", UIController.editIncident);
            //ApiBuilder.put("/{id}/estado", UIController.updateIncidentState);
        });
    }
}





