package org.utn;

import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.HttpStatus;
import io.javalin.rendering.JavalinRenderer;
import org.utn.presentation.api.url_mappings.IncidentsResource;
import org.utn.presentation.api.url_mappings.TelegramBotResource;
import org.utn.presentation.api.url_mappings.UIResource;
import org.utn.presentation.incidents_load.CsvReader;
import org.utn.presentation.worker.IncidentsCsvWorker;
import org.utn.presentation.worker.MQCLient;

import java.io.IOException;

public class ServerApi {

    public static void main(String[] args) throws IOException, TimeoutException {

        InitWorker();

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

    private static void InitWorker() throws IOException, TimeoutException {
        String[] requiredEnvVars = {"QUEUE_HOST", "QUEUE_USERNAME", "QUEUE_PASSWORD", "QUEUE_NAME"};

        Map<String, String> env = System.getenv();

        //forzamos un fail fast acá por si las variables no están cargadas correctamente
        //me salto este error porque no las tenia cargadas en el proyecto...
        for (String var : requiredEnvVars) {
            if (env.get(var) == null) {
                throw new IllegalArgumentException("Falta la variable de entorno: " + var);
            }
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(env.get("QUEUE_HOST"));
        factory.setUsername(env.get("QUEUE_USERNAME"));
        factory.setPassword(env.get("QUEUE_PASSWORD"));
        factory.setVirtualHost(env.get("QUEUE_USERNAME"));
        String queueName = env.get("QUEUE_NAME");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            IncidentsCsvWorker worker = new IncidentsCsvWorker(channel, queueName, new CsvReader());
            worker.init();
        }
    }


    /*
    Comento el metodo de Gabo para comparar
    private static void InitWorker() throws IOException, TimeoutException {
        Map<String, String> env = System.getenv();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(env.get("QUEUE_HOST"));
        factory.setUsername(env.get("QUEUE_USERNAME"));
        factory.setPassword(env.get("QUEUE_PASSWORD"));
        factory.setVirtualHost(env.get("QUEUE_USERNAME"));
        String queueName = env.get("QUEUE_NAME");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        IncidentsCsvWorker worker = new IncidentsCsvWorker(channel,queueName, new CsvReader());
        worker.init();
    }*/

    private static void initTemplateEngine() {
    JavalinRenderer.register(
            (path, model, context) -> { // Template render
              Handlebars handlebars = new Handlebars();
              Template template = null;
              try {
                template = handlebars.compile("templates/" + path.replace(".hbs", ""));
                return template.apply(model);
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
