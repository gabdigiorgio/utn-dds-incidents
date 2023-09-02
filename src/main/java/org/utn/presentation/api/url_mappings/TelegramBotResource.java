package org.utn.presentation.api.url_mappings;

import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import org.utn.presentation.api.controllers.StartBotController;

public class TelegramBotResource implements EndpointGroup{

    @Override
    public void addEndpoints() {
        ApiBuilder.path("/bot", () -> {
            ApiBuilder.post("/", new StartBotController());
        });
    }

}
