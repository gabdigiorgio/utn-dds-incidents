package org.utn.bot.handlers;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.bot.TelegramBot;
import org.utn.bot.TelegramUserBot;
import org.utn.bot.TelegramUserBotState;
import org.utn.dominio.incidente.Incidencia;
import org.utn.infraestructura.persistencia.MemRepoIncidencias;

import java.util.List;

import static org.utn.bot.handlers.Shows.*;

public class IncidentsByStateHandle {
    public static void quantityInput(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        if (messageText.equals("0")) {
            showMainMenu(telegramUserBot,bot);
        } else{
            //TODO VALIDAR QUE EL MENSAJE SEA UN NUMERO
            telegramUserBot.setStatus(TelegramUserBotState.MENU_GET_INCIDENTS_BY_STATE_WAITING_RESPONSE_STATE);
            telegramUserBot.setIncidentsQuantity(Integer.parseInt(messageText));
            showGetStatusIncidents(telegramUserBot,bot);
        }
    }

    public static void stateInput(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        if (messageText.equals("0")) {
            showMainMenu(telegramUserBot,bot);
        } else{
            //TODO VALIDAR QUE EL ESTADO SEA VALIDO
            final MemRepoIncidencias repoIncidencias= MemRepoIncidencias.obtenerInstancia();
            List<Incidencia> incidencias =  repoIncidencias.obtenerIncidenciasByEstado(telegramUserBot.getIncidentsQuantity(),messageText);
            showIncidents(telegramUserBot,bot,incidencias);
        }
    }

}
