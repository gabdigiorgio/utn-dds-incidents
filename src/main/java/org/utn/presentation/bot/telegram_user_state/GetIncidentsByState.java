package org.utn.presentation.bot.telegram_user_state;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.application.IncidentManager;
import org.utn.domain.incident.Incident;
import org.utn.presentation.bot.telegram_user.TelegramUserBot;
import org.utn.presentation.bot.UtilsBot;
import org.utn.presentation.bot.Shows;
import org.utn.utils.StringValidatorUtils;

import java.util.List;

import static org.utn.presentation.bot.Shows.*;

public class GetIncidentsByState extends UserBotState {

    @Override
    public String getStateName() {
        return "GetIncidentsFirstReport";
    }

    private IncidentManager manager;
    public GetIncidentsByState(IncidentManager manager) {
        this.manager = manager;
    }

    @Override
    public void execute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        switch (subState) {
            case START -> startExecute(telegramUserBot, bot);
            case WAITING_RESPONSE_QUANTITY -> waitingResponseQuantityExecute(telegramUserBot, messageText, bot);
            case WAITING_RESPONSE_STATE -> waitingResponseStateExecute(telegramUserBot, messageText, bot);
        }
    }

    private void startExecute(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        showGetQuantityIncidents(telegramUserBot, bot);
        this.setSubState(SubState.WAITING_RESPONSE_QUANTITY);
    }

    private void waitingResponseQuantityExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        if (messageText.equals("/menu")) {
            Shows.showMainMenu(telegramUserBot, bot);
        } else {
            if (UtilsBot.validateIsNumber(telegramUserBot, messageText, bot)) {
                return;
            }
            telegramUserBot.setIncidentsQuantity(Integer.parseInt(messageText));
            showGetStatusIncidents(telegramUserBot, bot);
            this.setSubState(SubState.WAITING_RESPONSE_STATE);
        }
    }

    private void waitingResponseStateExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        if (messageText.equals("/menu")) {
            telegramUserBot.setState(new MainMenu());
            telegramUserBot.execute(messageText, bot);
        } else {
            if (!StringValidatorUtils.isUserState(messageText)) {
                invalidMessage(telegramUserBot, bot);
                showPossibleStates(telegramUserBot, bot);
                return;
            }

            List<Incident> incidents = manager.getIncidents(telegramUserBot.getIncidentsQuantity(), null, messageText, null);
            showIncidents(telegramUserBot, bot, incidents);
        }
    }

}
