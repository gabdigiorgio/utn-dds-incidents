package org.utn.controllers;

import java.util.List;
import java.util.Objects;

import org.utn.aplicacion.GestorIncidencia;
import org.utn.controllers.inputs.CreateIncident;
import org.utn.controllers.inputs.EditIncident;
import org.utn.dominio.incidencia.Incidencia;
import org.utn.persistencia.MemRepoIncidencias;

import io.javalin.http.Handler;

public class IncidentsController {
  static GestorIncidencia gestor = new GestorIncidencia(MemRepoIncidencias.obtenerInstancia());

//   // validate two dependent query parameters:
// Instant fromDate = ctx.queryParam("from", Instant.class).get();
// Instant toDate = ctx.queryParam("to", Instant.class)
//         .check(it -> it.isAfter(fromDate), "'to' has to be after 'from'")
//         .get();

// // validate a json body:
// MyObject myObject = ctx.bodyValidator(MyObject.class)
//         .check(obj -> obj.myObjectProperty == someValue)
//         .getOrThrow();

  public static Handler getIncidents = ctx -> {
    Integer limit = ctx.queryParamAsClass("limit", Integer.class).getOrDefault(10);
    String orderBy = ctx.queryParamAsClass("orderBy", String.class).getOrDefault("createdAt");
    String status = ctx.queryParamAsClass("status", String.class).getOrDefault(null);
    String place = ctx.queryParamAsClass("place", String.class).getOrDefault(null);

    // get incidents
    List<Incidencia> incidents = gestor.getIncidents(limit, orderBy, status, place);
    ctx.json("result: " + incidents);
    ctx.status(200);
  };

  public static Handler createIncident = ctx -> {
    try {
      CreateIncident data = ctx.bodyAsClass(CreateIncident.class);

      // create incident
      Incidencia newIncident = gestor.createIncident(data);

      ctx.json("id: " + newIncident.getId());
      ctx.status(200);
    } catch(Exception error) {
      ctx.json("error: " + error.getMessage());
      ctx.status(400);
    }
  };

  public static Handler editIncident = ctx -> {
    try {
      Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
      EditIncident data = ctx.bodyAsClass(EditIncident.class);
  
      // edit incident
      Incidencia editedIncident = gestor.editIncident(id, data);
  
      ctx.json("id: " + editedIncident.toString());
      ctx.status(200);
      
    } catch(Exception error) {
      ctx.json("error: " + error.getMessage());
      ctx.status(400);
    }
  };

  public static Handler deleteIncident = ctx -> {
    try {
      int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
  
      // delete incident
      gestor.deleteIncident(id);

      ctx.json("result: true");
      ctx.status(200);

    } catch(Exception error) {
      ctx.json("error: " + error.getMessage());
      ctx.status(400);
    }
  };
}
