package org.utn.presentacion.api.url_mappings;

import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import org.utn.presentacion.api.controllers.InitializateBotController;

public class TelegramBotResource implements EndpointGroup{

    @Override
    public void addEndpoints() {
        ApiBuilder.path("/bot", () -> {
            ApiBuilder.post("/", new InitializateBotController());
        });
    }

}
