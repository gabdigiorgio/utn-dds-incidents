package org.utn.presentation.api.url_mappings;

import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import org.utn.application.IncidentManager;
import org.utn.presentation.api.controllers.IncidentsController;
import org.utn.presentation.api.controllers.UIController;

import javax.persistence.EntityManagerFactory;

public class UIResource implements EndpointGroup {

    IncidentManager manager;

    public UIResource(IncidentManager manager) {
        this.manager = manager;
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
                ctx.render("404.hbs");
            });

            ApiBuilder.get("/nosotros", ctx -> {
                ctx.render("404.hbs");
            });

            ApiBuilder.get("/contacto", ctx -> {
                ctx.render("404.hbs");
            });
        });

        UIController UIController = new UIController(manager);
        ApiBuilder.path("/ui/incidents", () -> {
            ApiBuilder.get("/", UIController.getIncidents);
            ApiBuilder.get("/upload_csv", UIController.createMassiveIncident);
            ApiBuilder.get("/new", UIController.createIncident);
            ApiBuilder.get("/edit/{id}", UIController.editIncident);
            ApiBuilder.get("/{id}", UIController.getIncident);
        });
    }
}





