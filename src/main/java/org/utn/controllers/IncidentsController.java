package org.utn.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.utn.aplicacion.IncidentManager;
import org.utn.controllers.inputs.ChangeStatus;
import org.utn.controllers.inputs.CreateIncident;
import org.utn.controllers.inputs.EditIncident;
import org.utn.controllers.inputs.ErrorResponse;
import org.utn.dominio.incidencia.Incident;
import org.utn.persistencia.MemIncidentsRepo;
import org.json.JSONObject;

import io.javalin.http.Handler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class IncidentsController {
  static IncidentManager manager = new IncidentManager(MemIncidentsRepo.getInstance());

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
    List<Incident> incidents = manager.getIncidents(limit, orderBy, status, place);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    String json = objectMapper.writeValueAsString(incidents);
    ctx.json(json);
    ctx.status(200);
  };

  public static Handler createIncident = ctx -> {
    try {
      CreateIncident data = ctx.bodyAsClass(CreateIncident.class);

      // create incident
      Incident newIncident = manager.createIncident(data);

      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
      objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

      String json = objectMapper.writeValueAsString(newIncident);

      ctx.json(json);

      ctx.status(200);
    } catch(Exception error) {
      ctx.json(parseErrorResponse(400,error.getMessage()));
      ctx.status(400);
    }
  };

  public static Handler editIncident = ctx -> {
    try {
      Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
      EditIncident data = ctx.bodyAsClass(EditIncident.class);
  
      // edit incident
      Incident editedIncident = manager.editIncident(id, data);

      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
      objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

      String json = objectMapper.writeValueAsString(editedIncident);

      ctx.json(json);
      ctx.status(200);
      
    } catch(Exception error) {
      ctx.json(parseErrorResponse(400,error.getMessage()));
      ctx.status(400);
    }
  };
  public static Handler updateIncidentStatus = ctx -> {
    try {
      Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
      ChangeStatus request = ctx.bodyAsClass(ChangeStatus.class);

      Incident editedIncident = manager.updateIncidentStatus(id, request);

      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
      objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

      String json = objectMapper.writeValueAsString(editedIncident);
      ctx.result(json).contentType("application/json");

      //ctx.json(objectMapper.writeValueAsString(editedIncident));
      ctx.status(200);

    } catch(Exception error) {
      ctx.json(parseErrorResponse(400,error.getMessage()));
      ctx.status(400);
    }
  };

  public static Handler deleteIncident = ctx -> {
    try {
      int id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
  
      // delete incident
      manager.deleteIncident(id);

      JSONObject result = new JSONObject();
      result.put("result", true);

      ctx.json(result.toString());
      ctx.status(200);

    } catch(Exception error) {

      ctx.json(parseErrorResponse(400,error.getMessage()));
      ctx.status(400);
    }
  };


  public static String parseErrorResponse(int statusCode, String errorMsg) throws JsonProcessingException {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.status =statusCode;
    errorResponse.message ="Bad Request";
    errorResponse.errors = Collections.singletonList(errorMsg);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    return objectMapper.writeValueAsString(errorResponse);
  }

}
