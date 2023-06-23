package org.utn.presentacion.bot;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.presentacion.bot.telegram_user.TelegramUserBot;
import org.utn.utils.StringValidatorUtils;

import static org.utn.presentacion.bot.Shows.*;

public class UtilsBot {

    // Cambiado a isNumber a modo de pregunta porque es un metodo booleano
    public static boolean isNumber(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        if (!StringValidatorUtils.isNumber(messageText)){
            invalidMessage(telegramUserBot,bot);
            showGetQuantityIncidents(telegramUserBot,bot);
            return true;
        }
        return false;
    }

    // Cambiado a hasCodePlaceFormat a modo de pregunta porque es booleano
    public static boolean hasCodePlaceFormat(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {

        if (!StringValidatorUtils.isCodePlaceFormat(messageText)) {
            invalidFormatCode(telegramUserBot, bot);
            showGetPlaceIncidents(telegramUserBot, bot);
            return false;
        }
        return true;
    }

}
