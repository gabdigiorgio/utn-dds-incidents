package org.utn.presentacion.api.controllers;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.javalin.http.UploadedFile;
import io.javalin.util.FileUtil;
import org.apache.http.entity.StringEntity;
import org.utn.aplicacion.GestorIncidencia;
import org.utn.presentacion.api.inputs.ChangeState;
import org.utn.presentacion.api.inputs.CreateIncident;
import org.utn.presentacion.api.inputs.EditIncident;
import org.utn.presentacion.api.inputs.ErrorResponse;
import org.utn.dominio.incidencia.Incidencia;
import org.utn.persistencia.DbIncidentsRepository;
import org.json.JSONObject;

import io.javalin.http.Handler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.utn.presentacion.carga_incidentes.ReaderCsv;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.ContentType;

import java.net.URI;

public class IncidentsController {
  static GestorIncidencia gestor = new GestorIncidencia(DbIncidentsRepository.obtenerInstancia());

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
      Incidencia newIncident = gestor.createIncident(data);

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
      Incidencia editedIncident = gestor.editIncident(id, data);

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
  public static Handler updateIncidentState = ctx -> {
    try {
      Integer id = Integer.parseInt(Objects.requireNonNull(ctx.pathParam("id")));
      ChangeState request = ctx.bodyAsClass(ChangeState.class);

      Incidencia editedIncident = gestor.updateIncidentState(id, request);

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
      gestor.deleteIncident(id);

      JSONObject result = new JSONObject();
      result.put("result", true);

      ctx.json(result.toString());
      ctx.status(200);

    } catch(Exception error) {

      ctx.json(parseErrorResponse(400,error.getMessage()));
      ctx.status(400);
    }
  };

  public static void sendToWorker(String payload) throws Exception {
    CloseableHttpClient httpclient = HttpClients.createDefault();
    Map<String, String> env = System.getenv();
    try {
      JSONObject json = new JSONObject();
      json.put("vhost", env.get("QUEUE_HOST"));
      json.put("routing_key", env.get("QUEUE_NAME"));
      json.put("delivery_mode", "1");
      json.put("payload_encoding", "string");
      json.put("properties", new JSONObject());
      json.put("payload", payload);

      StringEntity params = new StringEntity(json.toString());
      HttpUriRequest httpPost = RequestBuilder.post()
              .setUri(new URI("https://gull.rmq.cloudamqp.com/api/exchanges/fvizvkea/amq.default/publish"))
              .addHeader("Authorization", env.get("QUEUE_AUTH"))
              .addHeader("Content-Type", "application/json; charset=UTF-8")
              .addHeader("Accept", "*/*")
              .addHeader("Accept-Encoding", "gzip, deflate, br")
              .setEntity(params)
              .build();

      CloseableHttpResponse response = httpclient.execute(httpPost);
      try {
        System.out.println(EntityUtils.toString(response.getEntity()));
      } finally {
        response.close();
      }
    } finally {
      httpclient.close();
    }
  }


  public static Handler createMassiveIncident = ctx -> {
    try {
      UploadedFile file = ctx.uploadedFile("file");
      if (file != null) {
        InputStream inputStream = new ByteArrayInputStream(file.content().readAllBytes());
        String text = new String(inputStream.readAllBytes(), "UTF-8");

        sendToWorker(text);
        ctx.status(200);
      }
      else {
        ctx.status(400);
        ctx.json("No se recibió ningún archivo.");
      }

    } catch(Exception error) {
      ctx.json(parseErrorResponse(400,error.getMessage()));
      ctx.status(400);
    }
  };

  public static String parseErrorResponse(int statusCode, String errorMsg) throws JsonProcessingException {
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.status=statusCode;
    errorResponse.message ="Bad Request";
    errorResponse.errors = Collections.singletonList(errorMsg);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    return objectMapper.writeValueAsString(errorResponse);
  }


}