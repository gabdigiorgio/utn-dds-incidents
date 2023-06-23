package org.utn.presentacion.bot.telegram_user_estado;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.aplicacion.IncidentManager;
import org.utn.dominio.incidencia.Incident;
import org.utn.presentacion.bot.telegram_user.TelegramUserBot;
import org.utn.presentacion.bot.UtilsBot;
import org.utn.presentacion.bot.Shows;

import java.util.List;

import static org.utn.presentacion.bot.Shows.*;

public class GetIncidentsLastReport extends UserBotStatus {

    private IncidentManager manager;
    public GetIncidentsLastReport(IncidentManager manager) {
        this.manager = manager;
    }

    @Override
    public String getStatusName() {
        return "GetIncidentsLastReport";
    }

    @Override
    public void execute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        switch (subStatus){
            case START -> startExecute(telegramUserBot,bot);
            case WAITING_RESPONSE_QUANTITY -> waitingResponseExecute(telegramUserBot,messageText,bot);
        }
    }

    private void startExecute(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        showGetQuantityIncidents(telegramUserBot,bot);
        this.setSubStatus(SubStatus.WAITING_RESPONSE_QUANTITY);
    }

    private void waitingResponseExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        if (messageText.equals("/menu")) {
            telegramUserBot.setStatus(new MainMenu());
            telegramUserBot.execute(messageText,bot);
        } else{
            if (UtilsBot.isNumber(telegramUserBot, messageText, bot)){return;}

            List<Incident> incidents = manager.getIncidents(Integer.parseInt(messageText), "createdAt", null, null);
            Shows.showIncidents(telegramUserBot,bot, incidents);
        }
    }
}
