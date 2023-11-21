package org.utn.presentation.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.presentation.bot.telegram_user.TelegramUserBot;
import org.utn.domain.incident.Incident;

import java.util.List;

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
        String msg = "✏️ Escribe \"/menu\" para ver las opciones disponibles ";
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

    public static void showGetPlaceIncidents(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "➡️ Escriba el código del lugar de las incidencias que desea visualizar\n"
                +"↩️ Si desea volver al menu anterior escriba 0️⃣";

        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }

    public static void showGetStatusIncidents(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "➡️ Escriba el estado de las incidencias que desea visualizar\n"
                +"↩️ Si desea volver al menu principal escriba 0️⃣";

        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }

    public static void showIncidents(TelegramUserBot telegramUserBot, TelegramBot bot, List<Incident> incidents) throws TelegramApiException {

        for (Incident incident : incidents) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(telegramUserBot.getId());

            String tmp_msg = msgFromIncident(incident);
            sendMessage.setText(tmp_msg);
            bot.execute(sendMessage);
        }

        showBackMainMenu(telegramUserBot,bot);
    }


    public static void invalidMessage(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "❌ La opción ingresada no es valida";
        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }
    public static void invalidFormatCode(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "❌ El código ingresado no cumple con el formato";
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
    public static void showPossibleStates(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "Los estados posibles de una incidencia son los siguientes:\n"
                +"▪️Asignado\n"
                +"▪️Confirmado\n"
                +"▪️Desestimado\n"
                +"▪️En progreso\n"
                +"▪️Reportado\n"
                +"▪️Solucionado\n";
        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }


    private static String msgFromIncident(Incident incidencia){
        StringBuilder msg = new StringBuilder();

        // Agregar encabezado de tabla
        msg.append("Codigo de catalogo: ").append(incidencia.getCatalogCode()).append("\n")
            .append("Fecha de reporte: ").append(incidencia.getReportDate()).append("\n")
            .append("Descripcion: ").append(incidencia.getDescription()).append("\n")
            .append("Estado: ").append(incidencia.getState().getStateName()).append("\n")
            .append("Operador: ").append(incidencia.getOperator()).append("\n")
            .append("Persona que lo reporto: ").append(incidencia.getReportedBy()).append("\n")
            .append("Fecha cierre: ").append(incidencia.getClosingDate()).append("\n")
            .append("Motivo rechazo: ").append(incidencia.getRejectedReason());

        return msg.toString();
    }

}
