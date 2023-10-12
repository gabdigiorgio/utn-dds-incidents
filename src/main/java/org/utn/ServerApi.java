package org.utn;

import java.util.function.Consumer;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.JavalinRenderer;
import org.utn.presentation.api.url_mappings.IncidentsResource;
import org.utn.presentation.api.url_mappings.TelegramBotResource;
import org.utn.presentation.api.url_mappings.UIResource;

import java.io.IOException;

public class ServerApi {

    public static void main(String[] args) {

        // TemplateEngine -Handlebars
        initTemplateEngine();

        Integer port = Integer.parseInt( System.getProperty("port", "8080"));
        Javalin server = Javalin.create().start(port);

        // bot
        server.routes(new TelegramBotResource());
        
        // API
        server.routes(new IncidentsResource());

        // UI
        server.routes(new UIResource());
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
