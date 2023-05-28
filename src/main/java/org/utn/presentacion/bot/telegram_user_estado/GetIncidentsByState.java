package org.utn.presentacion.bot.telegram_user_estado;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.aplicacion.ObtenedorIncidencias;
import org.utn.dominio.incidencia.Incidencia;
import org.utn.persistencia.MemRepoIncidencias;
import org.utn.presentacion.bot.telegram_user.TelegramUserBot;
import org.utn.presentacion.bot.UtilsBot;
import org.utn.presentacion.bot.Shows;
import org.utn.utils.StringValidatorUtils;

import java.util.List;
import java.util.regex.Pattern;

import static org.utn.presentacion.bot.Shows.*;

public class GetIncidentsByState extends UserBotEstado {

    private final ObtenedorIncidencias obtenedorIncidencias;

    @Override
    public String getNombreEstado() {
        return "GetIncidentsFirstReport";
    }

    public GetIncidentsByState(ObtenedorIncidencias obtenedorIncidencias) {

        this.obtenedorIncidencias = obtenedorIncidencias;
    }

    @Override
    public void execute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        switch (subEstado) {
            case START -> startExecute(telegramUserBot, bot);
            case WAITING_RESPONSE_QUANTITY -> waitingResponseQuantityExecute(telegramUserBot, messageText, bot);
            case WAITING_RESPONSE_STATE -> waitingResponseStateExecute(telegramUserBot, messageText, bot);
        }
    }

    private void startExecute(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        showGetQuantityIncidents(telegramUserBot, bot);
        this.setSubEstado(SubEstado.WAITING_RESPONSE_QUANTITY);
    }

    private void waitingResponseQuantityExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        if (messageText.equals("/menu")) {
            Shows.showMainMenu(telegramUserBot, bot);
        } else {
            if (UtilsBot.validateIsNumber(telegramUserBot, messageText, bot)) {
                return;
            }
            telegramUserBot.setIncidentsQuantity(Integer.parseInt(messageText));
            showGetStatusIncidents(telegramUserBot, bot);
            this.setSubEstado(SubEstado.WAITING_RESPONSE_STATE);
        }
    }

    private void waitingResponseStateExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        if (messageText.equals("/menu")) {
            telegramUserBot.setEstado(new MainMenu(obtenedorIncidencias));
            telegramUserBot.execute(messageText, bot);
        } else {
            if (!StringValidatorUtils.isUserState(messageText)) {
                invalidMessage(telegramUserBot, bot);
                showPossibleStates(telegramUserBot, bot);
                return;
            }

            List<Incidencia> incidencias = obtenedorIncidencias.obtenerIncidenciasByEstado(telegramUserBot.getIncidentsQuantity(), messageText);
            showIncidents(telegramUserBot, bot, incidencias);
        }
    }

}
