package org.utn;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.JavalinRenderer;
import org.utn.modules.ManagerFactory;
import org.utn.presentation.api.url_mappings.IncidentsResource;
import org.utn.presentation.api.url_mappings.TelegramBotResource;
import org.utn.presentation.api.url_mappings.UIResource;
import org.utn.presentation.api.url_mappings.UsersResource;

import java.io.IOException;
import java.util.function.Consumer;

public class ServerApi {

    public static void main(String[] args) {

        var incidentManager = ManagerFactory.createIncidentManager();
        var jobManager = ManagerFactory.createJobManager();
        var usersManager = ManagerFactory.createUserManager();

        // TemplateEngine -Handlebars
        initTemplateEngine();

        Integer port = Integer.parseInt( System.getProperty("port", "8080"));
        Javalin server = Javalin.create().start(port);

        // bot
        server.routes(new TelegramBotResource());
        
        // API
        server.routes(new IncidentsResource(incidentManager, jobManager, createObjectMapper()));
        server.routes(new UsersResource(usersManager, jobManager, createObjectMapper()));

        // UI
        server.routes(new UIResource(incidentManager, jobManager));
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
                staticFiles.directory = "/public";
            });
        };
    }
}
