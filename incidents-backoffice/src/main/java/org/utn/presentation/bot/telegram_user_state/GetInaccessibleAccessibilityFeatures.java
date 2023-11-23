package org.utn.presentation.bot.telegram_user_state;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.application.IncidentManager;
import org.utn.domain.incident.Incident;
import org.utn.presentation.bot.Shows;
import org.utn.presentation.bot.UtilsBot;
import org.utn.presentation.bot.telegram_user.TelegramUserBot;
import org.utn.utils.StringValidatorUtils;

import java.util.List;

import static org.utn.presentation.bot.Shows.*;

public class GetInaccessibleAccessibilityFeatures extends UserBotState {

    private IncidentManager incidentManager;

    public GetInaccessibleAccessibilityFeatures(IncidentManager incidentManager) {
        this.incidentManager = incidentManager;
    }
    @Override
    public String getStateName() {
        return "GetInaccessibleAccessibilityFeatures";
    }

    @Override
    public void execute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        switch (subState) {
            case START -> startExecute(telegramUserBot, bot);
            case WAITING_RESPONSE_QUANTITY -> waitingResponseQuantityExecute(telegramUserBot, messageText, bot);
            case WAITING_RESPONSE_LINE -> waitingResponseLineExecute(telegramUserBot, messageText, bot);
            case WAITING_RESPONSE_STATION -> waitingResponseStationExecute(telegramUserBot, messageText, bot);
        }
    }

    private void startExecute(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        showGetQuantityInaccessibleAccessibilityFeatures(telegramUserBot,bot);
        this.setSubState(SubState.WAITING_RESPONSE_QUANTITY);
    }

    private void waitingResponseQuantityExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        if (messageText.equals("/menu")) {
            Shows.showMainMenu(telegramUserBot, bot);
        } else {
            if (UtilsBot.validateIsNumber(telegramUserBot, messageText, bot)) {
                invalidMessage(telegramUserBot, bot);
                showGetQuantityInaccessibleAccessibilityFeatures(telegramUserBot,bot);
                return;
            }
            telegramUserBot.setInaccessibleAccessibilityFeaturesQuantity(Integer.parseInt(messageText));
            showGetLineInaccessibleAccessibilityFeatures(telegramUserBot, bot);
            this.setSubState(SubState.WAITING_RESPONSE_LINE);
        }
    }

    private void waitingResponseLineExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        if (messageText.equals("/menu")) {
            telegramUserBot.setState(new MainMenu());
            telegramUserBot.execute(messageText, bot);
        } else {
            if (!StringValidatorUtils.isLine(messageText)) {
                invalidMessage(telegramUserBot, bot);
                showPossibleLines(telegramUserBot, bot);
                return;
            }
            telegramUserBot.setLine(messageText);
            showGetStationInaccessibleAccessibilityFeatures(telegramUserBot, bot);
            this.setSubState(SubState.WAITING_RESPONSE_STATION);
        }
    }

    private void waitingResponseStationExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        if (messageText.equals("/menu")) {
            telegramUserBot.setState(new MainMenu());
            telegramUserBot.execute(messageText,bot);
        } else{

            var inaccessibleAccessibilityFeatures = incidentManager.getInaccessibleAccessibilityFeatures(telegramUserBot.getInaccessibleAccessibilityFeaturesQuantity(),
                    telegramUserBot.getLine(), messageText);
            Shows.showInaccessibleAccessibilityFeatures(telegramUserBot,bot, inaccessibleAccessibilityFeatures);
        }
    }
}
