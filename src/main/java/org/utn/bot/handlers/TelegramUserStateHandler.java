package org.utn.bot.handlers;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.bot.TelegramBot;
import org.utn.bot.TelegramUserBot;
import static org.utn.bot.handlers.IncidentsOrderByFirstReportHandle.handleIncidentsOrderByFirstReport;
import static org.utn.bot.handlers.IncidentsOrderByLastReportHandle.handleIncidentsOrderByLastReport;

public class TelegramUserStateHandler {
    public static void handleUserState(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        switch (telegramUserBot.getStatus()){
            case WELCOME_CHAT -> WelcomeHandle.execute(telegramUserBot,bot);
            case INIT_CHAT -> MainMenuHandle.options(telegramUserBot,messageText,bot);
            case MAIN_MENU -> MainMenuHandle.input(telegramUserBot,messageText,bot);
            case MENU_GET_INCIDENTS_ORDER_BY_LAST_REPORT -> handleIncidentsOrderByLastReport(telegramUserBot,messageText,bot);
            case MENU_GET_INCIDENTS_ORDER_BY_FIRST_REPORT -> handleIncidentsOrderByFirstReport(telegramUserBot,messageText,bot);
            case MENU_GET_INCIDENTS_BY_STATE -> IncidentsByStateHandle.quantityInput(telegramUserBot,messageText,bot);
            case MENU_GET_INCIDENTS_BY_STATE_WAITING_RESPONSE_STATE -> IncidentsByStateHandle.stateInput(telegramUserBot,messageText,bot);
            case MENU_GET_INCIDENTS_BY_PLACE -> IncidentsByPlaceHandle.placeInput(telegramUserBot,messageText,bot);

        }
    }
}
