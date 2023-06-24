package org.utn.presentacion.bot.telegram_user_estado;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.aplicacion.GestorIncidencia;
import org.utn.dominio.incidencia.Incidencia;
import org.utn.presentacion.bot.telegram_user.TelegramUserBot;
import org.utn.presentacion.bot.UtilsBot;
import org.utn.presentacion.bot.Shows;

import java.util.List;

import static org.utn.presentacion.bot.Shows.invalidMessage;
import static org.utn.presentacion.bot.Shows.showIncidents;

public class GetIncidentsByPlace extends UserBotEstado{
    private GestorIncidencia gestor;
    public GetIncidentsByPlace(GestorIncidencia gestor) {
        this.gestor = gestor;
    }

    @Override
    public String getNombreEstado() {
        return "GetIncidentsFirstReport";
    }

    @Override
    public void execute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        switch (subEstado){
            case START -> startExecute(telegramUserBot,bot);
            case WAITING_RESPONSE -> waitingResponseExecute(telegramUserBot,messageText,bot);
        }
    }

    private void startExecute(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        Shows.showGetPlaceIncidents(telegramUserBot,bot);
        this.setSubEstado(SubEstado.WAITING_RESPONSE);
    }

    private void waitingResponseExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        if (messageText.equals("/menu")) {
            telegramUserBot.setEstado(new MainMenu());
            telegramUserBot.execute(messageText,bot);
        } else{
            if (messageText.isEmpty()){ invalidMessage(telegramUserBot,bot); }
            if (!UtilsBot.validateCodePlaceFormat(telegramUserBot,messageText,bot)){return;}

            List<Incidencia> incidencias = gestor.getIncidents(10, null, null, messageText);
            showIncidents(telegramUserBot,bot,incidencias);
        }
    }
}
