package org.utn;

import io.javalin.Javalin;
import org.utn.presentacion.api.controllers.IniciarBotController;

public class ServerApi {
    public static void main(String[] args) {

        Integer port = Integer.parseInt( System.getProperty("port", "8080"));
        Javalin app = Javalin.create().start(port);


        app.post("/bot", new IniciarBotController());

    }

}
