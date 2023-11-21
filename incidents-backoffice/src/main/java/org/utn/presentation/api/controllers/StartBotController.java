package org.utn.presentation.api.controllers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.utn.TelegramBot;

import java.util.HashMap;
import java.util.Map;

public class StartBotController implements Handler {
    @Override
    public void handle(Context ctx) throws Exception {

        Map<String,String> rta = new HashMap<String, String>();
        if (TelegramBot.isBotStarted()) {
            rta.put("bot_status", "El bot ya se encontraba iniciado.");
        } else {
            TelegramBot.main(null);
            rta.put("bot_status", "Se inicio la ejecución del BOT correctamente.");
        }
        ctx.json(rta);
    }
}
