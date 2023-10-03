package org.utn.presentation.api.controllers;

import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;
import javassist.NotFoundException;
import org.utn.application.IncidentManager;
import org.utn.domain.incident.Incident;
import org.utn.persistence.DbIncidentsRepository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.utn.presentation.api.controllers.IncidentsController.parseErrorResponse;
import static org.utn.presentation.api.controllers.IncidentsController.sendToWorker;

public class UIController {
    static IncidentManager manager = new IncidentManager(DbIncidentsRepository.getInstance());

    public static Handler getIncident = ctx -> {
        try {
            Map<String, Object> model = new HashMap<>();
            int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));

            // get incident by id
            Incident incident = manager.getIncident(id);
            model.put("incident", incident);
            ctx.render("incident.hbs", model);

        } catch (NotFoundException notFoundError) {
            ctx.status(404);
            ctx.result("Incidencia no encontrada");
        } catch (Exception error) {
            ctx.json(parseErrorResponse(400, error.getMessage()));
            ctx.status(400);
        }
    };

    public static Handler createMassiveIncident = ctx -> {
        try {
            ctx.render("incident_upload_csv.hbs");
        } catch (Exception error) {
            ctx.status(500);
            ctx.html("Error al procesar el archivo CSV: " + error.getMessage());
        }
    };

}

