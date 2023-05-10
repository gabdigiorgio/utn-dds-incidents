package org.utn.bot;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.utn.bot.handlers.Shows.invalidMessage;
import static org.utn.bot.handlers.Shows.showGetQuantityIncidents;

public class UtilsBot {

    public static boolean validateIsNumber(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        if (!UtilsBot.isNumber(messageText)){
            System.out.println("NO ES UN NUMERO");
            invalidMessage(telegramUserBot,bot);
            showGetQuantityIncidents(telegramUserBot,bot);
            return false;
        }
        return true;
    }
    private static boolean isNumber(String s)  {
        if (s == null || s.equals("")) {
            return false;
        }
        return s.chars().allMatch(Character::isDigit);
    }


}
