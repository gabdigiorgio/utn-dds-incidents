package org.utn.presentation.api.url_mappings;

import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import org.utn.domain.users.Role;
import org.utn.presentation.api.controllers.UIController;

public class UIResource implements EndpointGroup {

    public UIResource() {
    }

    @Override
    public void addEndpoints() {

        // con esto podríamos generar un home y otras cositas, a evaluar si corresponde o no hacer esto

        /*ApiBuilder.path("/", () -> {

            ApiBuilder.get("/", ctx -> {
                ctx.render("index.hbs");
            });

            // Agrega una nueva ruta '/about'
            ApiBuilder.get("/faqs", ctx -> {
                ctx.render("faqs.hbs");
            });

            ApiBuilder.get("/ui/incidents/upload_csv", ctx -> {
                ctx.render("incident_upload_csv.hbs");
            });

            ApiBuilder.get("/ui/incidents/login", ctx -> {
                ctx.render("login.hbs");
            });

            ApiBuilder.get("/error", ctx -> {
                ctx.render("404.hbs");
            });

            ApiBuilder.get("/ui/incidents/inaccessible_accessibility_features", ctx -> {
                ctx.render("inaccessible_accessibility_features.hbs");
            });

        });*/


        UIController UIController = new UIController();
        ApiBuilder.path("/ui/incidents", () -> {
            ApiBuilder.get("/login", UIController.getLogin, Role.ANYONE);
            ApiBuilder.get("/", UIController.getIncidents, Role.ANYONE);
            ApiBuilder.get("/inaccessible_accessibility_features", UIController.getInaccessibleAccessibilityFeatures, Role.ANYONE);
            ApiBuilder.get("/upload_csv", UIController.createMassiveIncident, Role.OPERATOR);
            ApiBuilder.get("/processing_csv_state/{id}", UIController.getCsvProcessingState, Role.OPERATOR);
            ApiBuilder.get("/new", UIController.createIncident, Role.USER, Role.OPERATOR);
            ApiBuilder.get("/edit/{id}/state", UIController.updateIncidentState, Role.OPERATOR);
            ApiBuilder.get("/edit/{id}", UIController.editIncident);
            ApiBuilder.get("/{id}", UIController.getIncident, Role.ANYONE);
        });
    }
}





