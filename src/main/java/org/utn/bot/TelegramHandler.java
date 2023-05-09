package org.utn.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramHandler {

    public static void handleUserState(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        switch (telegramUserBot.getStatus()){
            case WELCOME_CHAT -> handleWelcome(telegramUserBot,bot);
            case INIT_CHAT -> handleMainMenuOptions(telegramUserBot,messageText,bot);
            case MAIN_MENU -> handleMainMenuInput(telegramUserBot,messageText,bot);
            case MENU_GET_INCIDENTS_ORDER_BY_LAST_REPORT -> handleIncidentsOrderByLastReport(telegramUserBot,messageText,bot);
        }
    }

    //------------- PRIVATE HANDLERS---------------------
    private static void handleWelcome(TelegramUserBot telegramUserBot,TelegramBot bot) throws TelegramApiException {
        showWelcomeMessage(telegramUserBot,bot);
        telegramUserBot.setStatus(TelegramUserBotState.INIT_CHAT);
    }

    private static void handleMainMenuOptions(TelegramUserBot telegramUserBot, String messageText,TelegramBot bot) throws TelegramApiException {

        if (messageText.equals("/menu")) {
            showMainMenu(telegramUserBot,bot);
        } else{
            invalidMessage(telegramUserBot,bot);
            showInitMessage(telegramUserBot,bot);
        }
    }

    private static void handleMainMenuInput(TelegramUserBot telegramUserBot, String message,TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg;

        switch (message) {
            case "1" -> {
                msg = "Selecciono la opción: Obtener un listado de las últimas N incidencias ordenado por las últimas reportadas";
                sendMessage.setText(msg);
                bot.execute(sendMessage);
                telegramUserBot.setStatus(TelegramUserBotState.MENU_GET_INCIDENTS_ORDER_BY_LAST_REPORT);
                showGetQuantityIncidents(telegramUserBot,bot);
            }
            //updateUserState(userId, UserState.State.SUBMENU_1);
            //showSubmenu1(chatId);
            case "2" -> {
                msg = "Se ingreso la opción 2 del menú principal";
                sendMessage.setText(msg);
                bot.execute(sendMessage);
                telegramUserBot.setStatus(TelegramUserBotState.SUBMENU_2);
            }
            //showSubmenu2(chatId);
            case "3" -> {
                msg = "Se ingreso la opción 3 del menú principal";
                sendMessage.setText(msg);
                bot.execute(sendMessage);
            }
            default -> {
                invalidMessage(telegramUserBot,bot);
                showMainMenu(telegramUserBot,bot);
            }
        }

    }

    private static void handleIncidentsOrderByLastReport(TelegramUserBot telegramUserBot, String messageText,TelegramBot bot) throws TelegramApiException {

        if (messageText.equals("0")) {
            showMainMenu(telegramUserBot,bot);
        } else{
            showIncidentsOrderByLastReport(telegramUserBot,bot);
        }
    }

    //------------------SHOWS ----------------------


    private static void showWelcomeMessage(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "Bienvenidos al bot de TPA SAMA - GRUPO 1";
        sendMessage.setText(msg);
        bot.execute(sendMessage);
        showInitMessage(telegramUserBot,bot);
    }
    private static void showInitMessage(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "Ingresa el mensaje \"/menu\" ver las opciones disponibles";
        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }

    private static void showMainMenu(TelegramUserBot user, TelegramBot bot){
        SendMessage response = new SendMessage();
        response.setChatId(user.getId());
        response.setText("""
                Escriba el número de la opción deseada:
                1. Obtener un listado de las últimas N incidencias ordenado por las últimas reportadas
                2. Obtener un listado de las últimas N incidencias ordenado por la más vieja
                3. Obtener N incidencias  por estado
                4. Obtener inicidencias segun lugar""");
        try {
            bot.execute(response);
            user.setStatus(TelegramUserBotState.MAIN_MENU);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static void showGetQuantityIncidents(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "Ingrese el número de incidencias que quiere visualizar\n"
                +"Si desea volver al menu anterior ingrese 0";

        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }

    private static void showIncidentsOrderByLastReport(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "ACA SE MOSTRARIAN LAS ULTIMAS INCICENDIAS REPORTADAS";
        sendMessage.setText(msg);
        bot.execute(sendMessage);
        showInitMessage(telegramUserBot,bot);
    }

    private static void invalidMessage(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "La opción ingresada no es valida";
        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }


}
