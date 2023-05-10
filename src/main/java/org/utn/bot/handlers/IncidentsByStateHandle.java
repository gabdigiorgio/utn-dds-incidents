package org.utn.bot.handlers;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.bot.TelegramBot;
import org.utn.bot.TelegramUserBot;
import org.utn.bot.TelegramUserBotState;
import org.utn.bot.UtilsBot;
import org.utn.dominio.incidente.Incidencia;
import org.utn.infraestructura.persistencia.MemRepoIncidencias;

import java.util.List;
import java.util.regex.Pattern;
import static org.utn.bot.handlers.Shows.*;

public class IncidentsByStateHandle {
    public static void quantityInput(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        if (messageText.equals("0")) {
            showMainMenu(telegramUserBot,bot);
        } else{
            if (!UtilsBot.validateIsNumber(telegramUserBot,messageText,bot)){return;}

            telegramUserBot.setStatus(TelegramUserBotState.MENU_GET_INCIDENTS_BY_STATE_WAITING_RESPONSE_STATE);
            telegramUserBot.setIncidentsQuantity(Integer.parseInt(messageText));
            showGetStatusIncidents(telegramUserBot,bot);
        }
    }

    public static void stateInput(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        if (messageText.equals("0")) {
            showMainMenu(telegramUserBot,bot);
        } else{
            if (!validarFormatoEstado(messageText)){
                invalidMessage(telegramUserBot,bot);
                showPossibleStates(telegramUserBot,bot);
                return;
            }

            final MemRepoIncidencias repoIncidencias= MemRepoIncidencias.obtenerInstancia();
            List<Incidencia> incidencias =  repoIncidencias.obtenerIncidenciasByEstado(telegramUserBot.getIncidentsQuantity(),messageText);
            showIncidents(telegramUserBot,bot,incidencias);
        }
    }


    private static boolean validarFormatoEstado(String estado)  {
        return validarExpresionRegular(estado, "^(Asignado|Confirmado|Desestimado|En progreso|Reportado|Solucionado)$");
    }
    private static boolean validarExpresionRegular(String valor, String expresionRegular)  {
        Pattern regex = Pattern.compile(expresionRegular);
        return regex.matcher(valor).matches();
    }

}
