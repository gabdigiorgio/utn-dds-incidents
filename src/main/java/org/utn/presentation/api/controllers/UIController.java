package org.utn.presentation.api.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.javalin.http.Handler;
import javassist.NotFoundException;
import org.utn.application.IncidentManager;
import org.utn.domain.incident.Incident;
import org.utn.persistence.DbIncidentsRepository;

import static org.utn.presentation.api.controllers.IncidentsController.parseErrorResponse;

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

}

