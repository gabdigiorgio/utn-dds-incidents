package org.utn;

import io.javalin.Javalin;

import org.utn.controllers.IncidentsController;
import org.utn.presentacion.api.controllers.IniciarBotController;

public class ServerApi {
    public static void main(String[] args) {
        Integer port = Integer.parseInt( System.getProperty("port", "8080"));
        Javalin server = Javalin.create().start(port);

        // bot
        server.post("/bot", new IniciarBotController());
        
        // incidents
        server.get("/incidents", IncidentsController.getIncidents);
        server.post("/incidents", IncidentsController.createIncident);
        server.post("/incidents/{id}", IncidentsController.editIncident);
        server.delete("/incidents/{id}", IncidentsController.deleteIncident);
    }

}
