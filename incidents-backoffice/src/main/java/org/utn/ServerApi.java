package org.utn;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.ForbiddenResponse;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.JavalinRenderer;
import org.utn.application.incident.ForbiddenOperationException;
import org.utn.application.users.exceptions.IncorrectPasswordException;
import org.utn.application.users.exceptions.MissingUserFieldsException;
import org.utn.application.users.exceptions.UserAlreadyExistsException;
import org.utn.application.users.exceptions.UserNotExistsException;
import org.utn.domain.incident.state.StateTransitionException;
import org.utn.presentation.api.CustomAccessManager;
import org.utn.presentation.api.controllers.IncidentsController;
import org.utn.presentation.api.url_mappings.IncidentsResource;
import org.utn.presentation.api.url_mappings.TelegramBotResource;
import org.utn.presentation.api.url_mappings.UIResource;
import org.utn.presentation.api.url_mappings.UsersResource;
import org.utn.utils.exceptions.validator.InvalidCatalogCodeException;
import org.utn.utils.exceptions.validator.InvalidDateException;

import javax.naming.OperationNotSupportedException;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.function.Consumer;

public class ServerApi {

    public static void main(String[] args) {

        initTemplateEngine();

        Integer port = Integer.parseInt(System.getProperty("port", "8080"));
        Javalin server = Javalin.create(config -> {
            config.accessManager(new CustomAccessManager());
            config.staticFiles.add(staticFiles -> {
                staticFiles.directory = "/public";
            });
        }).start(port);

        setupExceptions(server);

        server.routes(new TelegramBotResource());

        server.routes(new IncidentsResource(createObjectMapper()));
        server.routes(new UIResource());
        server.routes(new UsersResource(createObjectMapper()));
    }

    private static void setupExceptions(Javalin server) {
        setupExceptionHandling(server, EntityNotFoundException.class, 404);
        setupExceptionHandling(server, ForbiddenOperationException.class, 403);
        setupExceptionHandling(server, ForbiddenResponse.class, 403);
        setupExceptionHandling(server, IllegalArgumentException.class, 400);
        setupExceptionHandling(server, StateTransitionException.class, 400);
        setupExceptionHandling(server, UnrecognizedPropertyException.class, 400);
        setupExceptionHandling(server, InvalidDateException.class, 400);
        setupExceptionHandling(server, InvalidCatalogCodeException.class, 400);
        setupExceptionHandling(server, OperationNotSupportedException.class, 400);
        setupExceptionHandling(server, MissingUserFieldsException.class, 400);
        setupExceptionHandling(server, UserNotExistsException.class, 400);
        setupExceptionHandling(server, UserAlreadyExistsException.class, 400);
        setupExceptionHandling(server, IncorrectPasswordException.class, 400);
        setupExceptionHandling(server, Exception.class, 500);
    }

    private static <T extends Exception> void setupExceptionHandling(Javalin server, Class<T> exceptionClass, int statusCode) {
        server.exception(exceptionClass, (e, ctx) -> {
            try {
                ctx.json(IncidentsController.parseErrorResponse(statusCode, e.getMessage()));
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

    private static void initTemplateEngine() {
        JavalinRenderer.register(
                (path, model, context) -> { // Template render, expresion lambda para definir la renderizacion
                    Handlebars hb = new Handlebars(); // le cambio el nombre a hb para identificarlo mejor
                    try {

                        Template tp = hb.compile("templates/" + path.replace(".hbs", ""));
                        return tp.apply(model);

                    } catch (IOException e) {
                        e.printStackTrace();
                        context.status(HttpStatus.NOT_FOUND);
                        return "No se encuentra la página indicada...";
                    }
                }, ".hbs" // Handlebars extension
        );
    }


    private static Consumer<JavalinConfig> config() {
        return config -> {
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/";
                staticFiles.directory = "/incidents-backoffice/src/main/resources/public";
            });
        };
    }
}
