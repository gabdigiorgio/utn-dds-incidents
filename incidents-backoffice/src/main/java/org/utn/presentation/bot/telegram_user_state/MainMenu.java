package org.utn.presentation.bot.telegram_user_state;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.application.incident.IncidentManager;
import org.utn.modules.ManagerFactory;
import org.utn.presentation.bot.telegram_user.TelegramUserBot;

import static org.utn.presentation.bot.Shows.invalidMessage;

public class MainMenu extends UserBotState {

    static IncidentManager incidentManager = ManagerFactory.createIncidentManager();

    public MainMenu() {
    }

    @Override
    public String getStateName() {
        return "MainMenu";
    }

    @Override
    public void execute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        switch (subState) {
            case START -> startExecute(telegramUserBot, bot);
            case WAITING_RESPONSE_OPTION -> waitingResponseExecute(telegramUserBot, messageText, bot);
        }
    }

    private void startExecute(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage response = new SendMessage();
        response.setChatId(telegramUserBot.getId());
        response.setText("""
                Escriba el número de la opción deseada:
                1️⃣ ☞ Obtener N incidencias (La mas recientes primero)
                2️⃣ ☞ Obtener N incidencias (La mas antigua primero)
                3️⃣ ☞ Obtener N incidencias  filtrando por estado
                4️⃣ ☞ Obtener las incidencias de un codigo de catalogo
                5️⃣ ☞ Obtener las medidas de accesibilidad inaccesibles filtrando por Linea y Estacion""");
        try {
            bot.execute(response);
            this.setSubState(SubState.WAITING_RESPONSE_OPTION);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void waitingResponseExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());

        switch (messageText) {
            case "1" -> {
                telegramUserBot.setState(new GetIncidentsLastReport(incidentManager));
                telegramUserBot.execute(messageText, bot);
            }
            case "2" -> {
                telegramUserBot.setState(new GetIncidentsFirstReport(incidentManager));
                telegramUserBot.execute(messageText, bot);
            }
            case "3" -> {
                telegramUserBot.setState(new GetIncidentsByState(incidentManager));
                telegramUserBot.execute(messageText, bot);
            }
            case "4" -> {
                telegramUserBot.setState(new GetIncidentsByCatalogCode(incidentManager));
                telegramUserBot.execute(messageText, bot);
            }
            case "5" -> {
                telegramUserBot.setState(new GetInaccessibleAccessibilityFeatures(incidentManager));
                telegramUserBot.execute(messageText, bot);
            }
            default -> invalidMessage(telegramUserBot, bot);
        }

    }
}
