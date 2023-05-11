package org.utn.bot.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.bot.TelegramBot;
import org.utn.bot.TelegramUserBot;
import org.utn.bot.TelegramUserBotState;

import static org.utn.bot.handlers.Shows.*;

public class MainMenuHandle {
    public static void options(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {

        if (messageText.equals("/menu")) {
            showMainMenu(telegramUserBot,bot);
        } else{
            invalidMessage(telegramUserBot,bot);
            showInitMessage(telegramUserBot,bot);
        }
    }

    public static void input(TelegramUserBot telegramUserBot, String message,TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg;

        switch (message) {
            case "1" -> {
                telegramUserBot.setStatus(TelegramUserBotState.MENU_GET_INCIDENTS_ORDER_BY_LAST_REPORT);
                showGetQuantityIncidents(telegramUserBot,bot);
            }
            case "2" -> {
                telegramUserBot.setStatus(TelegramUserBotState.MENU_GET_INCIDENTS_ORDER_BY_FIRST_REPORT);
                showGetQuantityIncidents(telegramUserBot,bot);
            }
            case "3" -> {
                telegramUserBot.setStatus(TelegramUserBotState.MENU_GET_INCIDENTS_BY_STATE);
                showGetQuantityIncidents(telegramUserBot,bot);
            }
            case "4" -> {
                telegramUserBot.setStatus(TelegramUserBotState.MENU_GET_INCIDENTS_BY_PLACE);
                showGetPlaceIncidents(telegramUserBot,bot);
            }
            default -> {
                invalidMessage(telegramUserBot,bot);
                showMainMenu(telegramUserBot,bot);
            }
        }

    }


}
