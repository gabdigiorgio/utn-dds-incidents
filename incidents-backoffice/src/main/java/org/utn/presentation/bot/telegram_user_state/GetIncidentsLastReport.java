package org.utn.presentation.bot.telegram_user_state;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.application.IncidentManager;
import org.utn.domain.incident.Incident;
import org.utn.presentation.bot.telegram_user.TelegramUserBot;
import org.utn.presentation.bot.UtilsBot;
import org.utn.presentation.bot.Shows;

import java.util.List;

import static org.utn.presentation.bot.Shows.*;

public class GetIncidentsLastReport extends UserBotState {

    private IncidentManager manager;
    public GetIncidentsLastReport(IncidentManager manager) {
        this.manager = manager;
    }

    @Override
    public String getStateName() {
        return "GetIncidentsLastReport";
    }

    @Override
    public void execute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        switch (subState){
            case START -> startExecute(telegramUserBot,bot);
            case WAITING_RESPONSE_QUANTITY -> waitingResponseExecute(telegramUserBot,messageText,bot);
        }
    }

    private void startExecute(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        showGetQuantityIncidents(telegramUserBot,bot);
        this.setSubState(SubState.WAITING_RESPONSE_QUANTITY);
    }

    private void waitingResponseExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        if (messageText.equals("/menu")) {
            telegramUserBot.setState(new MainMenu());
            telegramUserBot.execute(messageText,bot);
        } else{
            if (UtilsBot.validateIsNumber(telegramUserBot, messageText, bot)){return;}

            List<Incident> incidents = manager.getIncidents(Integer.parseInt(messageText), "createdAt", null, null);
            Shows.showIncidents(telegramUserBot,bot,incidents);
        }
    }
}
