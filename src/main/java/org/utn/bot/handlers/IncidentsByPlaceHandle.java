package org.utn.bot.handlers;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.bot.TelegramBot;
import org.utn.bot.TelegramUserBot;
import org.utn.bot.TelegramUserBotState;
import org.utn.bot.UtilsBot;
import org.utn.dominio.incidente.Incidencia;
import org.utn.infraestructura.persistencia.MemRepoIncidencias;

import java.util.List;

import static org.utn.bot.handlers.Shows.showIncidents;
import static org.utn.bot.handlers.Shows.showMainMenu;
import static org.utn.bot.handlers.Shows.invalidMessage;

public class IncidentsByPlaceHandle {

    public static void placeInput(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        if (messageText.equals("0")) {
            showMainMenu(telegramUserBot,bot);
        } else{
            if (messageText.isEmpty()){ invalidMessage(telegramUserBot,bot); }

            final MemRepoIncidencias repoIncidencias= MemRepoIncidencias.obtenerInstancia();
            List<Incidencia> incidencias =  repoIncidencias.obtenerIncidenciasByPlace(messageText);
            showIncidents(telegramUserBot,bot,incidencias);
        }
    }

}
