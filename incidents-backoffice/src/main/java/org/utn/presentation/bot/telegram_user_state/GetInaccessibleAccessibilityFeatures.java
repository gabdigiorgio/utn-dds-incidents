package org.utn.presentation.bot.telegram_user_state;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.application.incident.IncidentManager;
import org.utn.presentation.api.dto.responses.StationResponse;
import org.utn.presentation.bot.Shows;
import org.utn.presentation.bot.UtilsBot;
import org.utn.presentation.bot.telegram_user.TelegramUserBot;

import java.io.IOException;
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

    private void waitingResponseQuantityExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException, IOException {
        if (messageText.equals("/menu")) {
            Shows.showMainMenu(telegramUserBot, bot);
        } else {
            if (UtilsBot.validateIsNumber(telegramUserBot, messageText, bot)) {
                invalidMessage(telegramUserBot, bot);
                showGetQuantityInaccessibleAccessibilityFeatures(telegramUserBot,bot);
                return;
            }
            telegramUserBot.setInaccessibleAccessibilityFeaturesQuantity(Integer.parseInt(messageText));
            showPossibleLines(telegramUserBot, bot);
            this.setSubState(SubState.WAITING_RESPONSE_LINE);
        }
    }

    private void waitingResponseLineExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        if (messageText.equals("/menu")) {
            telegramUserBot.setState(new MainMenu());
            telegramUserBot.execute(messageText, bot);
        } else {
            if (waitingResponseSetLine(telegramUserBot, messageText) == -1) {
                invalidMessage(telegramUserBot, bot);
                showPossibleLines(telegramUserBot, bot);
                return;
            }
            showPossibleStations(telegramUserBot, bot);
            this.setSubState(SubState.WAITING_RESPONSE_STATION);
        }
    }

    private void waitingResponseStationExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        if (messageText.equals("/menu")) {
            telegramUserBot.setState(new MainMenu());
            telegramUserBot.execute(messageText,bot);
        } else{
            if(waitingResponseSetStation(telegramUserBot, bot, messageText) == -1)
            {
                invalidMessage(telegramUserBot, bot);
                showPossibleStations(telegramUserBot, bot);
                return;
            }

            var inaccessibleAccessibilityFeatures = incidentManager.getAccessibilityFeatures(telegramUserBot.getInaccessibleAccessibilityFeaturesQuantity(), "inaccessible",
                   telegramUserBot.getLine(), telegramUserBot.getStation(), null, null);
            Shows.showInaccessibleAccessibilityFeatures(telegramUserBot,bot, inaccessibleAccessibilityFeatures);
        }
    }

    public int waitingResponseSetStation(TelegramUserBot telegramUserBot, TelegramBot bot, String messageText) {
        try {
            int stationIndex = Integer.parseInt(messageText) - 1;
            List<StationResponse> possibleStations = telegramUserBot.getPossibleStations();

            if (possibleStations != null && stationIndex >= 0 && stationIndex < possibleStations.size()) {
                StationResponse selectedStation = possibleStations.get(stationIndex);
                telegramUserBot.setStation(selectedStation.getId());
                return 0;
            } else {
                return -1;
            }
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private int waitingResponseSetLine(TelegramUserBot telegramUserBot, String messageText) {
        try {
            int index = Integer.parseInt(messageText) - 1;

            if (index >= 0 && index < telegramUserBot.getPossibleLines().size()) {
                var selectedLine = telegramUserBot.getPossibleLines().get(index);

                telegramUserBot.setLine(selectedLine.getId());
                telegramUserBot.setLineIndex(index);
                return index;
            } else {
                return -1;
            }
        } catch (NumberFormatException e) {
            return -1;
        }
    }

}
