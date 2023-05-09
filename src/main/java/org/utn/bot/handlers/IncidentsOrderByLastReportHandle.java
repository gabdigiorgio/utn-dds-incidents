package org.utn.bot.handlers;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.bot.TelegramBot;
import org.utn.bot.TelegramUserBot;

import static org.utn.bot.handlers.Shows.showIncidentsOrderByLastReport;
import static org.utn.bot.handlers.Shows.showMainMenu;

public class IncidentsOrderByLastReportHandle {
    public static void handleIncidentsOrderByLastReport(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        if (messageText.equals("/menu")) {
            showMainMenu(telegramUserBot,bot);
        } else{
            showIncidentsOrderByLastReport(telegramUserBot,bot);
        }
    }
}
