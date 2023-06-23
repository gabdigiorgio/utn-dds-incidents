package org.utn;

import io.javalin.Javalin;

import org.utn.presentacion.api.url_mappings.IncidentsResource;
import org.utn.presentacion.api.url_mappings.TelegramBotResource;

public class ServerAPI {
    public static void main(String[] args) {
        try {
            Integer port = Integer.parseInt(System.getProperty("port", "8080"));
            Javalin server = Javalin.create().start(port);

            // bot
            server.routes(new TelegramBotResource());

            // incidents
            server.routes(new IncidentsResource());
        } catch (Exception e){  // le meto un catch al buen server api
            System.out.println("There was an error at Javalin launch");
            e.printStackTrace();
        }
    }

}
