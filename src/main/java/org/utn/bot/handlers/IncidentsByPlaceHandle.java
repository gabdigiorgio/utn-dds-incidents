package org.utn.bot.handlers;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.bot.TelegramBot;
import org.utn.bot.TelegramUserBot;
import org.utn.dominio.incidente.Incidencia;
import org.utn.infraestructura.persistencia.MemRepoIncidencias;

import java.util.List;

import static org.utn.bot.handlers.Shows.showIncidents;
import static org.utn.bot.handlers.Shows.showMainMenu;

public class IncidentsByPlaceHandle {
    public static void incidentsByPlaceHandle(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        if (messageText.equals("0")) {
            showMainMenu(telegramUserBot,bot);
        } else{
            final MemRepoIncidencias repoIncidencias= MemRepoIncidencias.obtenerInstancia();
            List<Incidencia> incidencias =  repoIncidencias.obtenerIncidencias(Integer.parseInt(messageText),"ordenarPorMasReciente");
            showIncidents(telegramUserBot,bot,incidencias);
        }
    }

}
