package org.utn;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import org.utn.presentation.url_mappings.AccessibilityResource;
import org.utn.presentation.url_mappings.LineResource;

import javax.persistence.EntityNotFoundException;

import static org.utn.presentation.api.controllers.AccessibilityController.parseErrorResponse;

public class ServerApi {
    public static void main(String[] args) {

        Integer port = Integer.parseInt(System.getProperty("port", "8080")); //TODO: cambiar puerto a 8080 en deploy!!!
        Javalin server = Javalin.create().start(port);
        setupExceptions(server);

        server.routes(new AccessibilityResource(createObjectMapper()));
        server.routes(new LineResource(createObjectMapper()));
    }

    private static void setupExceptions(Javalin server) {
        setupExceptionHandling(server, EntityNotFoundException.class, 404);
        setupExceptionHandling(server, IllegalArgumentException.class, 400);
        setupExceptionHandling(server, UnrecognizedPropertyException.class, 400);
        setupExceptionHandling(server, Exception.class, 500);
    }

    private static <T extends Exception> void setupExceptionHandling(Javalin server, Class<T> exceptionClass, int statusCode) {
        server.exception(exceptionClass, (e, ctx) -> {
            try {
                ctx.json(parseErrorResponse(statusCode, e.getMessage()));
            } catch (JsonProcessingException ex) {
                ctx.status(statusCode);
            }
            ctx.status(statusCode);
        });
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }
}