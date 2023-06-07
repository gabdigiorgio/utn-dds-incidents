package org.utn.presentacion.bot.telegram_user_estado;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.aplicacion.GestorIncidencia;
import org.utn.dominio.incidencia.Incidencia;
import org.utn.presentacion.bot.telegram_user.TelegramUserBot;
import org.utn.presentacion.bot.UtilsBot;
import org.utn.presentacion.bot.Shows;

import java.util.List;

import static org.utn.presentacion.bot.Shows.*;

public class GetIncidentsLastReport extends UserBotEstado{

    private GestorIncidencia gestor;
    public GetIncidentsLastReport(GestorIncidencia gestor) {
        this.gestor = gestor;
    }

    @Override
    public String getNombreEstado() {
        return "GetIncidentsLastReport";
    }

    @Override
    public void execute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        switch (subEstado){
            case START -> startExecute(telegramUserBot,bot);
            case WAITING_RESPONSE_QUANTITY -> waitingResponseExecute(telegramUserBot,messageText,bot);
        }
    }

    private void startExecute(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        showGetQuantityIncidents(telegramUserBot,bot);
        this.setSubEstado(SubEstado.WAITING_RESPONSE_QUANTITY);
    }

    private void waitingResponseExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        if (messageText.equals("/menu")) {
            telegramUserBot.setEstado(new MainMenu());
            telegramUserBot.execute(messageText,bot);
        } else{
            if (UtilsBot.validateIsNumber(telegramUserBot, messageText, bot)){return;}

            List<Incidencia> incidencias = gestor.getIncidents(Integer.parseInt(messageText), "createdAt", null, null);
            Shows.showIncidents(telegramUserBot,bot,incidencias);
        }
    }
}
