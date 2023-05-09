package org.utn.bot.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.bot.TelegramBot;
import org.utn.bot.TelegramUserBot;
import org.utn.bot.TelegramUserBotState;

public class Shows {
    static void showWelcomeMessage(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "¡Hola! \uD83D\uDE00 Bienvenido al bot de TPA SAMA - GRUPO 1";
        sendMessage.setText(msg);
        bot.execute(sendMessage);
        showInitMessage(telegramUserBot,bot);
    }
    public static void showInitMessage(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "✏️ Escribe \"/menu\" ver las opciones disponibles ";
        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }

    public static void showMainMenu(TelegramUserBot user, TelegramBot bot){
        SendMessage response = new SendMessage();
        response.setChatId(user.getId());
        response.setText("""
                Escriba el número de la opción deseada:
                1️⃣ ☞ Obtener N incidencias (La mas recientes primero)
                2️⃣ ☞ Obtener N incidencias (La mas antigua primero)
                3️⃣ ☞ Obtener N incidencias  filtrando por estado
                4️⃣ ☞ Obtener las incidencias de un codigo de catalogo""");
        try {
            bot.execute(response);
            user.setStatus(TelegramUserBotState.MAIN_MENU);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void showGetQuantityIncidents(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "➡️ Escriba el número de incidencias que desea visualizar\n"
                +"↩️ Si desea volver al menu anterior escriba 0️⃣";

        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }

    public static void showIncidentsOrderByLastReport(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "ACA SE MOSTRARIAN LAS ULTIMAS INCICENDIAS REPORTADAS";
        sendMessage.setText(msg);
        bot.execute(sendMessage);
        showBackMainMenu(telegramUserBot,bot);
    }

    public static void invalidMessage(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "❌ La opción ingresada no es valida";
        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }
    public static void showBackMainMenu(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "✏️ Escribe \"/menu\" para volver al menú principal";
        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }


}
