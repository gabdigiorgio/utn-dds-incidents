package org.utn.presentation.bot.telegram_user_state;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.application.IncidentManager;
import org.utn.presentation.bot.Shows;
import org.utn.presentation.bot.UtilsBot;
import org.utn.presentation.bot.telegram_user.TelegramUserBot;

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
            if(stationChoice(telegramUserBot, bot, messageText) == -1)
            {
                invalidMessage(telegramUserBot, bot);
                showPossibleStations(telegramUserBot, bot);
                return;
            }

            var inaccessibleAccessibilityFeatures = incidentManager.getInaccessibleAccessibilityFeatures(telegramUserBot.getInaccessibleAccessibilityFeaturesQuantity(),
                    telegramUserBot.getLine(), telegramUserBot.getStation());
            //Shows.showInaccessibleAccessibilityFeatures(telegramUserBot,bot, inaccessibleAccessibilityFeatures);
        }
    }

    public int stationChoice(TelegramUserBot telegramUserBot, TelegramBot bot, String messageText) {
        int stationIndex = Integer.parseInt(messageText) - 1;
        String[][] stationsPerLine = {
                {"Plaza de Mayo", "Perú", "Piedras", "Lima", "Sáenz Peña", "Congreso", "Pasco", "Alberti", "Plaza Miserere", "Loria", "Castro Barros", "Río de Janeiro", "Acoyte", "Primera Junta"},
                {"Leandro N. Alem", "Florida", "Carlos Pellegrini", "Uruguay", "Callao", "Pasteur", "Pueyrredón", "Carlos Gardel", "Medrano", "Ángel Gallardo", "Malabia", "Dorrego", "Federico Lacroze"},
                {"Retiro", "General San Martín", "Lavalle", "Diagonal Norte", "Avenida de Mayo", "Moreno", "Independencia", "San Juan", "Constitución"},
                {"Catedral", "9 de Julio", "Tribunales", "Callao", "Facultad de Medicina", "Pueyrredón", "Agüero", "Bulnes", "Scalabrini Ortiz", "Plaza Italia", "Palermo"},
                {"Bolívar", "Belgrano", "Independencia", "San José", "Entre Ríos", "Pichincha", "Jujuy", "General Urquiza", "Boedo", "Avenida La Plata"},
                {"Facultad de Derecho", "Las Heras", "Santa Fe", "Córdoba", "Corrientes", "Once", "Venezuela", "Humberto I", "Inclán", "Caseros", "Parque Patricios", "Hospitales"}
        };

        int lineIndex = telegramUserBot.getLineIndex();

        if (lineIndex != -1 && stationIndex >= 0 && stationIndex < stationsPerLine[lineIndex].length) {
            String selectedStation = stationsPerLine[lineIndex][stationIndex];
            telegramUserBot.setStation(selectedStation);
            return 0;
        } else {
            return -1;
        }
    }

    private int waitingResponseSetLine(TelegramUserBot telegramUserBot, String messageText) {
        switch (messageText) {
            case "1" -> {
                telegramUserBot.setLine("Linea A");
                telegramUserBot.setLineIndex(0);
                return 0;
            }
            case "2" -> {
                telegramUserBot.setLine("Linea B");
                telegramUserBot.setLineIndex(1);
                return 1;
            }
            case "3" -> {
                telegramUserBot.setLine("Linea C");
                telegramUserBot.setLineIndex(2);
                return 2;
            }
            case "4" -> {
                telegramUserBot.setLine("Linea D");
                telegramUserBot.setLineIndex(3);
                return 3;
            }
            case "5" -> {
                telegramUserBot.setLine("Linea E");
                telegramUserBot.setLineIndex(4);
                return 4;
            }
            case "6" -> {
                telegramUserBot.setLine("Linea H");
                telegramUserBot.setLineIndex(5);
                return 5;
            }
            default -> {
                return  -1;
            }
        }
    }
}
