package org.utn.presentacion.bot.telegram_user_estado;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.aplicacion.IncidentManager;
import org.utn.dominio.incidencia.Incident;
import org.utn.presentacion.bot.telegram_user.TelegramUserBot;
import org.utn.presentacion.bot.UtilsBot;
import org.utn.presentacion.bot.Shows;

import java.util.List;

import static org.utn.presentacion.bot.Shows.invalidMessage;
import static org.utn.presentacion.bot.Shows.showIncidents;

public class GetIncidentsByPlace extends UserBotStatus {
    private IncidentManager manager;
    public GetIncidentsByPlace(IncidentManager manager) {
        this.manager = manager;
    }

    @Override
    public String getStatusName() {
        return "GetIncidentsFirstReport";
    }

    @Override
    public void execute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        switch (subStatus){
            case START -> startExecute(telegramUserBot,bot);
            case WAITING_RESPONSE -> waitingResponseExecute(telegramUserBot,messageText,bot);
        }
    }

    private void startExecute(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        Shows.showGetPlaceIncidents(telegramUserBot,bot);
        this.setSubStatus(SubStatus.WAITING_RESPONSE);
    }

    private void waitingResponseExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        if (messageText.equals("/menu")) {
            telegramUserBot.setStatus(new MainMenu());
            telegramUserBot.execute(messageText,bot);
        } else{
            if (messageText.isEmpty()){ invalidMessage(telegramUserBot,bot); }
            if (!UtilsBot.hasCodePlaceFormat(telegramUserBot,messageText,bot)){return;}

            List<Incident> incidents = manager.getIncidents(10, null, null, messageText);
            showIncidents(telegramUserBot,bot, incidents);
        }
    }
}
