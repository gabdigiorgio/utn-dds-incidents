package org.utn.presentation.api.url_mappings;

import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import org.utn.application.IncidentManager;
import org.utn.application.JobManager;
import org.utn.presentation.api.controllers.UIController;

public class UIResource implements EndpointGroup {

    IncidentManager incidentManager;
    JobManager jobManager;

    public UIResource(IncidentManager incidentManager, JobManager jobManager) {

        this.incidentManager = incidentManager;
        this.jobManager = jobManager;
    }

    @Override
    public void addEndpoints() {

        // con esto podríamos generar un home y otras cositas, a evaluar si corresponde o no hacer esto
        ApiBuilder.path("/", () -> {
            ApiBuilder.get("/", ctx -> {
                ctx.render("index.hbs");
            });

            // Agrega una nueva ruta '/about'
            ApiBuilder.get("/faqs", ctx -> {
                ctx.render("faqs.hbs");
            });

            ApiBuilder.get("/nosotros", ctx -> {
                ctx.render("404.hbs");
            });

            ApiBuilder.get("/contacto", ctx -> {
                ctx.render("404.hbs");
            });
        });

        UIController UIController = new UIController(incidentManager, jobManager);
        ApiBuilder.path("/ui/incidents", () -> {
            ApiBuilder.get("/", UIController.getIncidents);
            ApiBuilder.get("/inaccessible_accessibility_features", UIController.getInaccessibleAccessibilityFeatures);
            ApiBuilder.get("/upload_csv", UIController.createMassiveIncident);
            ApiBuilder.get("/processing_csv_state/{id}", UIController.getCsvProcessingState);
            ApiBuilder.get("/new", UIController.createIncident);
            ApiBuilder.get("/edit/{id}/state", UIController.updateIncidentState);
            ApiBuilder.get("/edit/{id}", UIController.editIncident);
            ApiBuilder.get("/{id}", UIController.getIncident);
        });
    }
}





