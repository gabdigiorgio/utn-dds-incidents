package org.utn.bot;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.utn.bot.handlers.Shows.*;

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

    public static boolean validateCodePlaceFormat(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        Pattern regex = Pattern.compile("^[a-zA-Z0-9]{4}-[a-zA-Z0-9]{2}$");
        Matcher matcher = regex.matcher(messageText);
        if (!matcher.matches()) {
            invalidFormatCode(telegramUserBot, bot);
            showGetPlaceIncidents(telegramUserBot, bot);
            return false;
        }
        return true;
    }

}
