package org.utn.presentacion.bot;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.presentacion.bot.telegram_user.TelegramUserBot;
import org.utn.utils.StringValidatorUtils;


import static org.utn.presentacion.bot.Shows.*;

public class UtilsBot {

    public static boolean validateIsNumber(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        if (!StringValidatorUtils.isNumber(messageText)){
            invalidMessage(telegramUserBot,bot);
            showGetQuantityIncidents(telegramUserBot,bot);
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