package org.utn.presentation.bot;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.presentation.bot.telegram_user.TelegramUserBot;
import org.utn.utils.StringValidatorUtils;

import static org.utn.presentation.bot.Shows.*;

public class UtilsBot {

    public static boolean validateIsNumber(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        if (!StringValidatorUtils.isNumber(messageText)){
            return true;
        }
        return false;
    }

    public static boolean validateCodePlaceFormat(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {

        if (!StringValidatorUtils.isCodePlaceFormat(messageText)) {
            invalidFormatCode(telegramUserBot, bot);
            showGetPlaceIncidents(telegramUserBot, bot);
            return false;
        }
        return true;
    }

}
