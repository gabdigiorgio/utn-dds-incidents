package org.utn.presentation.bot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.domain.incident.Incident;
import org.utn.presentation.bot.telegram_user.TelegramUserBot;

import java.io.IOException;
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

    public static void showGetQuantityInaccessibleAccessibilityFeatures(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "➡️ Escriba el número de medidas de accesibilidad inaccesibles que desea visualizar\n"
                +"↩️ Si desea volver al menu anterior escriba 0️⃣";

        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }

    public static void showGetLineInaccessibleAccessibilityFeatures(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "➡️ Escriba la linea de subte de las medidas de accesibilidad inaccesibles que desea visualizar\n"
                +"↩️ Si desea volver al menu anterior escriba 0️⃣";

        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }

    public static void showGetStationInaccessibleAccessibilityFeatures(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "➡️ Escriba la estación de las medidas de accesibilidad inaccesibles que desea visualizar\n"
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

    public static void showInaccessibleAccessibilityFeatures(TelegramUserBot telegramUserBot,
                                                             TelegramBot bot, String inaccessibleAccessibilityFeature) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String tmp_msg = msgFromAccessibilityFeature(inaccessibleAccessibilityFeature);
        sendMessage.setText(tmp_msg);
        bot.execute(sendMessage);
        showBackMainMenu(telegramUserBot, bot);
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

    public static void showPossibleLines(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "Elija la linea de subte:\n"
                + "1️⃣ ☞ 🔵 Linea A\n"
                + "2️⃣ ☞ 🔴 Linea B\n"
                + "3️⃣ ☞ 🔵 Linea C\n"
                + "4️⃣ ☞ 🟢 Linea D\n"
                + "5️⃣ ☞ 🟣 Linea E\n"
                + "6️⃣ ☞ 🟡 Linea H\n";
        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }

    public static void showPossibleStations(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());

        String[][] stationsPerLine = {
                {"Plaza de Mayo", "Perú", "Piedras", "Lima", "Sáenz Peña", "Congreso", "Pasco", "Alberti", "Plaza Miserere", "Loria", "Castro Barros", "Río de Janeiro", "Acoyte", "Primera Junta"},
                {"Leandro N. Alem", "Florida", "Carlos Pellegrini", "Uruguay", "Callao", "Pasteur", "Pueyrredón", "Carlos Gardel", "Medrano", "Ángel Gallardo", "Malabia", "Dorrego", "Federico Lacroze"},
                {"Retiro", "General San Martín", "Lavalle", "Diagonal Norte", "Avenida de Mayo", "Moreno", "Independencia", "San Juan", "Constitución"},
                {"Catedral", "9 de Julio", "Tribunales", "Callao", "Facultad de Medicina", "Pueyrredón", "Agüero", "Bulnes", "Scalabrini Ortiz", "Plaza Italia", "Palermo"},
                {"Bolívar", "Belgrano", "Independencia", "San José", "Entre Ríos", "Pichincha", "Jujuy", "General Urquiza", "Boedo", "Avenida La Plata"},
                {"Facultad de Derecho", "Las Heras", "Santa Fe", "Córdoba", "Corrientes", "Once", "Venezuela", "Humberto I", "Inclán", "Caseros", "Parque Patricios", "Hospitales"}
        };

        String selectedLine = telegramUserBot.getLine();
        int lineIndex = telegramUserBot.getLineIndex();
        if (lineIndex != -1) {
            StringBuilder stationsMsg = new StringBuilder("Estaciones de la " + selectedLine + ":\n");
            for (int i = 0; i < stationsPerLine[lineIndex].length; i++) {
                stationsMsg.append((i + 1)).append("️⃣ ☞ ").append(stationsPerLine[lineIndex][i]).append("\n");
            }
            sendMessage.setText(stationsMsg.toString());
        } else {
            sendMessage.setText("Línea no encontrada.");
        }
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

    public static String msgFromAccessibilityFeature(String inaccessibleAccessibilityFeatures) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(inaccessibleAccessibilityFeatures);

            StringBuilder formattedJson = new StringBuilder("Medidas de Accesibilidad Inaccesibles:\n");

            for (JsonNode featureNode : jsonNode) {
                String catalogCode = featureNode.get("catalogCode").asText();
                String type = translateType(featureNode.get("type").asText());
                String status = translateStatus(featureNode.get("status").asText());
                JsonNode stationNode = featureNode.get("station");
                String stationName = stationNode.get("name").asText();
                String stationLine = stationNode.get("line").asText();

                formattedJson.append("\n🔍 Código de Catálogo: ").append(catalogCode)
                        .append("\n🛠️ Tipo: ").append(type)
                        .append("\n🚦 Estado: ").append(status)
                        .append("\n🚉 Estación: ").append(stationName)
                        .append("\n🛤️ Línea: ").append(stationLine)
                        .append("\n---------------------------");
            }

            return formattedJson.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al formatear la información de accesibilidad.";
        }
    }

    private static String translateType(String type) {
        switch (type) {
            case "ELEVATOR":
                return "Ascensor";
            case "ESCALATOR":
                return "Escalera Mecánica";
            case "RAMP":
                return "Rampa";
            case "SIGNAGE":
                return "Señalización";
            default:
                return type;
        }
    }

    private static String translateStatus(String status) {
        switch (status) {
            case "FUNCTIONAL":
                return "Funcional";
            case "INACCESSIBLE":
                return "Inaccesible";
            default:
                return status;
        }
    }



}
