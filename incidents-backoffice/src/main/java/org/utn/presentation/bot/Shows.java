package org.utn.presentation.bot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.domain.accessibility_feature.AccessibilityFeature;
import org.utn.domain.accessibility_feature.Line;
import org.utn.domain.accessibility_feature.Station;
import org.utn.domain.incident.Incident;
import org.utn.modules.ManagerFactory;
import org.utn.presentation.api.dto.responses.AccessibilityFeatureResponse;
import org.utn.presentation.api.dto.responses.IncidentResponse;
import org.utn.presentation.api.dto.responses.LineResponse;
import org.utn.presentation.api.dto.responses.StationResponse;
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
        showInitMessage(telegramUserBot, bot);
    }

    public static void showInitMessage(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "✏️ Escribe \"/menu\" para ver las opciones disponibles ";
        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }

    public static void showMainMenu(TelegramUserBot user, TelegramBot bot) {
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
                + "↩️ Si desea volver al menu anterior escriba 0️⃣";

        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }

    public static void showGetQuantityInaccessibleAccessibilityFeatures(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "➡️ Escriba el número de medidas de accesibilidad inaccesibles que desea visualizar\n"
                + "↩️ Si desea volver al menu anterior escriba 0️⃣";

        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }

    public static void showGetLineInaccessibleAccessibilityFeatures(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "➡️ Escriba la linea de subte de las medidas de accesibilidad inaccesibles que desea visualizar\n"
                + "↩️ Si desea volver al menu anterior escriba 0️⃣";

        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }

    public static void showGetStationInaccessibleAccessibilityFeatures(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "➡️ Escriba la estación de las medidas de accesibilidad inaccesibles que desea visualizar\n"
                + "↩️ Si desea volver al menu anterior escriba 0️⃣";

        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }

    public static void showGetPlaceIncidents(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "➡️ Escriba el código de catálogo de las incidencias que desea visualizar\n"
                + "↩️ Si desea volver al menu anterior escriba 0️⃣";

        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }

    public static void showGetStatusIncidents(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "➡️ Escriba el estado de las incidencias que desea visualizar\n"
                + "↩️ Si desea volver al menu principal escriba 0️⃣";

        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }

    public static void showIncidents(TelegramUserBot telegramUserBot, TelegramBot bot, List<IncidentResponse> incidents) throws TelegramApiException {

        for (IncidentResponse incident : incidents) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(telegramUserBot.getId());

            String tmp_msg = msgFromIncident(incident);
            sendMessage.setText(tmp_msg);
            bot.execute(sendMessage);
        }

        showBackMainMenu(telegramUserBot, bot);
    }

    public static void showInaccessibleAccessibilityFeatures(TelegramUserBot telegramUserBot,
                                                      TelegramBot bot, List<AccessibilityFeature> inaccessibleAccessibilityFeatures)
            throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());

        var accessibilityFeaturesResponse = inaccessibleAccessibilityFeatures.stream().
                map(Shows::mapToAccessibilityFeatureResponse).toList();

        String tmp_msg = msgFromAccessibilityFeature(accessibilityFeaturesResponse);
        sendMessage.setText(tmp_msg);
        bot.execute(sendMessage);
        showBackMainMenu(telegramUserBot, bot);
    }

    private static AccessibilityFeatureResponse mapToAccessibilityFeatureResponse(AccessibilityFeature feature) {
        AccessibilityFeatureResponse response = new AccessibilityFeatureResponse();
        response.setCatalogCode(feature.getCatalogCode());
        response.setType(feature.getType());
        response.setStatus(feature.getStatus());
        response.setStation(feature.getStation());
        response.setLine(feature.getLine());
        return response;
    }

    private static LineResponse mapToLineResponse(Line line) {
        LineResponse response = new LineResponse();
        response.setId(line.getId());
        response.setName(line.getName());
        return response;
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
                + "▪️Asignado\n"
                + "▪️Confirmado\n"
                + "▪️Desestimado\n"
                + "▪️En progreso\n"
                + "▪️Reportado\n"
                + "▪️Solucionado\n";
        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }

    public static void showPossibleLines(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException, IOException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        var incidentManager = ManagerFactory.createIncidentManager();
        var lines = incidentManager.getLines();
        var linesResponse = lines.stream().map(Shows::mapToLineResponse).toList();

        StringBuilder msgBuilder = new StringBuilder("Elija la linea de subte:\n");
        for (int i = 0; i < linesResponse.size(); i++) {
            msgBuilder.append(i + 1).append("️⃣ ☞ ").append(linesResponse.get(i).getName()).append("\n");
        }
        String msg = msgBuilder.toString();
        telegramUserBot.setPossibleLines(linesResponse);

        sendMessage.setText(msg);
        bot.execute(sendMessage);
    }

    public static void showPossibleStations(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException, IOException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());

        var incidentManager = ManagerFactory.createIncidentManager();
        var stations = incidentManager.getStationsFromLine(telegramUserBot.getLine());
        var stationsResponse = stations.stream().map(Shows::mapToStationResponse).toList();

        String selectedLine = telegramUserBot.getLine();
        int lineIndex = telegramUserBot.getLineIndex();

        if (lineIndex != -1 && !stationsResponse.isEmpty()) {
            StringBuilder stationsMsg = new StringBuilder("Estaciones de la " + selectedLine + ":\n");

            for (int i = 0; i < stationsResponse.size(); i++) {
                stationsMsg.append((i + 1)).append("️⃣ ☞ ").append(stationsResponse.get(i).getName()).append("\n");
            }

            sendMessage.setText(stationsMsg.toString());
            telegramUserBot.setPossibleStations(stationsResponse);
        } else {
            sendMessage.setText("No hay estaciones para esa linea.");
            bot.execute(sendMessage);
            showBackMainMenu(telegramUserBot, bot);
            return;
        }
        bot.execute(sendMessage);
    }



    private static StationResponse mapToStationResponse(Station station) {
        StationResponse response = new StationResponse();
        response.setId(station.getId());
        response.setName(station.getName());
        return response;
    }

    private static String msgFromIncident(IncidentResponse incident) {
        StringBuilder msg = new StringBuilder();

        // Agregar encabezado de tabla
        msg.append("Codigo de catalogo: ").append(incident.getCatalogCode()).append("\n")
                .append("Fecha de reporte: ").append(incident.getReportDate()).append("\n")
                .append("Descripcion: ").append(incident.getDescription()).append("\n")
                .append("Estado: ").append(incident.getState()).append("\n")
                .append("Operador: ").append(incident.getOperator()).append("\n")
                .append("Persona que lo reporto: ").append(incident.getReporterEmail()).append("\n")
                .append("Fecha cierre: ").append(incident.getClosingDate()).append("\n")
                .append("Motivo rechazo: ").append(incident.getRejectedReason());

        return msg.toString();
    }

    public static String msgFromAccessibilityFeature(List<AccessibilityFeatureResponse> inaccessibleAccessibilityFeatures) {
        StringBuilder formattedText = new StringBuilder("Medidas de Accesibilidad Inaccesibles:\n");

        try {
            for (AccessibilityFeatureResponse feature : inaccessibleAccessibilityFeatures) {
                String catalogCode = feature.getCatalogCode();
                String type = translateType(feature.getType());
                String status = translateStatus(feature.getStatus());
                String stationName = feature.getStation();
                String stationLine = feature.getLine();

                formattedText.append("\n🔍 Código de Catálogo: ").append(catalogCode)
                        .append("\n🛠️ Tipo: ").append(type)
                        .append("\n🚦 Estado: ").append(status)
                        .append("\n🚉 Estación: ").append(stationName)
                        .append("\n🛤️ Línea: ").append(stationLine)
                        .append("\n---------------------------");
            }

            return formattedText.toString();
        } catch (Exception e) {
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
