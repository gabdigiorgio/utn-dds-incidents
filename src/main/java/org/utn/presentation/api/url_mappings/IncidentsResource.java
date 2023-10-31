package org.utn.presentation.api.url_mappings;

import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import org.utn.presentation.api.controllers.IncidentsController;

import javax.persistence.EntityManagerFactory;

public class IncidentsResource implements EndpointGroup {
    EntityManagerFactory entityManagerFactory;

    public IncidentsResource(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void addEndpoints() {
        IncidentsController incidentsController = new IncidentsController(entityManagerFactory);
        ApiBuilder.path("/api/incidents", () -> {
            ApiBuilder.get("/", incidentsController.getIncidents);
            ApiBuilder.get("/{id}", incidentsController.getIncident);
            ApiBuilder.post("/", incidentsController.createIncident);
            ApiBuilder.put("/{id}", incidentsController.editIncident);
            ApiBuilder.delete("/{id}", incidentsController.deleteIncident);
            ApiBuilder.put("/{id}/estado", incidentsController.updateIncidentState);
            ApiBuilder.post("/upload_csv", incidentsController.createMassiveIncident);
        });
    }
}





