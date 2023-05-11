package org.utn.bot.handlers;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.bot.TelegramBot;
import org.utn.bot.TelegramUserBot;
import org.utn.bot.TelegramUserBotState;

import static org.utn.bot.handlers.Shows.showWelcomeMessage;

public class WelcomeHandle {
    public static void execute(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        showWelcomeMessage(telegramUserBot,bot);
        telegramUserBot.setStatus(TelegramUserBotState.INIT_CHAT);
    }

}
