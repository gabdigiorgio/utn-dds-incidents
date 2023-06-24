package org.utn.presentacion.bot.telegram_user_estado;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.aplicacion.IncidentManager;
import org.utn.dominio.incidencia.Incident;
import org.utn.presentacion.bot.telegram_user.TelegramUserBot;
import org.utn.presentacion.bot.UtilsBot;
import org.utn.presentacion.bot.Shows;
import org.utn.utils.StringValidatorUtils;

import java.util.List;

import static org.utn.presentacion.bot.Shows.*;

public class GetIncidentsByStatus extends UserBotStatus {

    @Override
    public String getStatusName() {
        return "GetIncidentsFirstReport";
    }

    private IncidentManager manager;
    public GetIncidentsByStatus(IncidentManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        switch (subStatus) {
            case START -> startExecute(telegramUserBot, bot);
            case WAITING_RESPONSE_QUANTITY -> waitingResponseQuantityExecute(telegramUserBot, messageText, bot);
            case WAITING_RESPONSE_STATUS -> waitingResponseStatusExecute(telegramUserBot, messageText, bot);
        }
    }

    private void startExecute(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        showGetQuantityIncidents(telegramUserBot, bot);
        this.setSubStatus(SubStatus.WAITING_RESPONSE_QUANTITY);
    }

    private void waitingResponseQuantityExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        if (messageText.equals("/menu")) {
            Shows.showMainMenu(telegramUserBot, bot);
        } else {
            if (UtilsBot.isNumber(telegramUserBot, messageText, bot)) {
                return;
            }
            telegramUserBot.setIncidentsQuantity(Integer.parseInt(messageText));
            showGetStatusIncidents(telegramUserBot, bot);
            this.setSubStatus(SubStatus.WAITING_RESPONSE_STATUS);
        }
    }

    private void waitingResponseStatusExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        if (messageText.equals("/menu")) {
            telegramUserBot.setStatus(new MainMenu());
            telegramUserBot.execute(messageText, bot);
        } else {
            if (!StringValidatorUtils.isUserStatus(messageText)) {
                invalidMessage(telegramUserBot, bot);
                showPossibleStatus(telegramUserBot, bot);
                return;
            }

            List<Incident> incidents = manager.getIncidents(telegramUserBot.getIncidentsQuantity(), null, messageText, null);
            showIncidents(telegramUserBot, bot, incidents);
        }
    }

}
