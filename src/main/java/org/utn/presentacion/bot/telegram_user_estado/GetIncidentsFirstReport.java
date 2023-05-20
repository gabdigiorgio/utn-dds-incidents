package org.utn.presentacion.bot.telegram_user_estado;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.aplicacion.ObtenedorIncidencias;
import org.utn.dominio.incidencia.Incidencia;
import org.utn.persistencia.MemRepoIncidencias;
import org.utn.presentacion.bot.telegram_user.TelegramUserBot;
import org.utn.presentacion.bot.UtilsBot;
import org.utn.presentacion.bot.Shows;

import java.util.List;

import static org.utn.presentacion.bot.Shows.showGetQuantityIncidents;

public class GetIncidentsFirstReport extends UserBotEstado{

    private final ObtenedorIncidencias obtenedorIncidencias;

    public GetIncidentsFirstReport(ObtenedorIncidencias obtenedorIncidencias) {
        this.obtenedorIncidencias = obtenedorIncidencias;
    }

    @Override
    public String getNombreEstado() {
        return "GetIncidentsFirstReport";
    }

    @Override
    public void execute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        switch (subEstado){
            case START -> startExecute(telegramUserBot,bot);
            case WAITING_RESPONSE_QUANTITY -> waitingResponseExecute(telegramUserBot,messageText,bot);
        }
    }

    private void startExecute(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        showGetQuantityIncidents(telegramUserBot,bot);
        this.setSubEstado(SubEstado.WAITING_RESPONSE_QUANTITY);
    }

    private void waitingResponseExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        if (messageText.equals("/menu")) {
            telegramUserBot.setEstado(new MainMenu(obtenedorIncidencias));
            telegramUserBot.execute(messageText,bot);
        } else{
            if (UtilsBot.validateIsNumber(telegramUserBot, messageText, bot)){return;}
            final MemRepoIncidencias repoIncidencias= MemRepoIncidencias.obtenerInstancia();
            List<Incidencia> incidencias =  repoIncidencias.obtenerIncidencias(Integer.parseInt(messageText),"ordenarPorLaMasVieja");
            Shows.showIncidents(telegramUserBot,bot,incidencias);
        }
    }
}
