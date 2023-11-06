package org.utn.presentation.api.url_mappings;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import org.utn.application.IncidentManager;
import org.utn.application.JobManager;
import org.utn.presentation.api.controllers.IncidentsController;

public class IncidentsResource implements EndpointGroup {
    IncidentManager manager;
    JobManager jobManager;
    ObjectMapper objectMapper;

    public IncidentsResource(IncidentManager manager, JobManager jobManager, ObjectMapper objectMapper) {
        this.manager = manager;
        this.jobManager = jobManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public void addEndpoints() {
        IncidentsController incidentsController = new IncidentsController(manager, jobManager,objectMapper);
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





