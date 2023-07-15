package org.utn;

import io.javalin.Javalin;
import org.utn.presentacion.api.controllers.IncidentsController;
import org.utn.presentacion.api.url_mappings.IncidentsResource;
import org.utn.presentacion.api.url_mappings.TelegramBotResource;

public class ServerApi {

    public static void main(String[] args) {
        Integer port = Integer.parseInt( System.getProperty("port", "8080"));
        Javalin server = Javalin.create().start(port);

        // bot
        server.routes(new TelegramBotResource());
        
        // incidents
        server.routes(new IncidentsResource());
    }
}
