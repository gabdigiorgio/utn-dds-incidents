package org.utn.presentation.bot.telegram_user_state;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.application.IncidentManager;
import org.utn.domain.incident.Incident;
import org.utn.presentation.bot.telegram_user.TelegramUserBot;
import org.utn.presentation.bot.UtilsBot;
import org.utn.presentation.bot.Shows;

import java.util.List;

import static org.utn.presentation.bot.Shows.invalidMessage;
import static org.utn.presentation.bot.Shows.showIncidents;

public class GetIncidentsByPlace extends UserBotState {
    private IncidentManager manager;
    public GetIncidentsByPlace(IncidentManager manager) {
        this.manager = manager;
    }

    @Override
    public String getStateName() {
        return "GetIncidentsFirstReport";
    }

    @Override
    public void execute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        switch (subState){
            case START -> startExecute(telegramUserBot,bot);
            case WAITING_RESPONSE -> waitingResponseExecute(telegramUserBot,messageText,bot);
        }
    }

    private void startExecute(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        Shows.showGetPlaceIncidents(telegramUserBot,bot);
        this.setSubState(SubState.WAITING_RESPONSE);
    }

    private void waitingResponseExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        if (messageText.equals("/menu")) {
            telegramUserBot.setState(new MainMenu());
            telegramUserBot.execute(messageText,bot);
        } else{
            if (messageText.isEmpty()){ invalidMessage(telegramUserBot,bot); }
            if (!UtilsBot.validateCodePlaceFormat(telegramUserBot,messageText,bot)){return;}

            List<Incident> incidents = manager.getIncidents(10, null, null, messageText);
            showIncidents(telegramUserBot,bot,incidents);
        }
    }
}
