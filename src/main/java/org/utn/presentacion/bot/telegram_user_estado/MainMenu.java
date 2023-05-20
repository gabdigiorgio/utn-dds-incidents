package org.utn.presentacion.bot.telegram_user_estado;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.aplicacion.ObtenedorIncidencias;
import org.utn.presentacion.bot.telegram_user.TelegramUserBot;

import static org.utn.presentacion.bot.Shows.*;

public class MainMenu extends UserBotEstado{

    private final ObtenedorIncidencias obtenedorIncidencias;

    public MainMenu(ObtenedorIncidencias obtenedorIncidencias){

        this.obtenedorIncidencias = obtenedorIncidencias;
    }

    @Override
    public String getNombreEstado() {
        return "MainMenu";
    }

    @Override
    public void execute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        switch (subEstado){
            case START -> startExecute(telegramUserBot,bot);
            case WAITING_RESPONSE_OPTION -> waitingResponseExecute(telegramUserBot,messageText,bot);
        }
    }

    private void startExecute(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage response = new SendMessage();
        response.setChatId(telegramUserBot.getId());
        response.setText("""
                Escriba el número de la opción deseada:
                1️⃣ ☞ Obtener N incidencias (La mas recientes primero)
                2️⃣ ☞ Obtener N incidencias (La mas antigua primero)
                3️⃣ ☞ Obtener N incidencias  filtrando por estado
                4️⃣ ☞ Obtener las incidencias de un codigo de catalogo""");
        try {
            bot.execute(response);
            this.setSubEstado(SubEstado.WAITING_RESPONSE_OPTION);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void waitingResponseExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg;

        switch (messageText) {
            case "1" -> {
                telegramUserBot.setEstado(new GetIncidentsLastReport(obtenedorIncidencias));
                telegramUserBot.execute(messageText,bot);
            }
            case "2" -> {
                telegramUserBot.setEstado(new GetIncidentsFirstReport(obtenedorIncidencias));
                telegramUserBot.execute(messageText,bot);
            }
            case "3" -> {
                telegramUserBot.setEstado(new GetIncidentsByState(obtenedorIncidencias));
                telegramUserBot.execute(messageText,bot);
            }
            case "4" -> {
                telegramUserBot.setEstado(new GetIncidentsByPlace(obtenedorIncidencias));
                telegramUserBot.execute(messageText,bot);
            }
            default -> invalidMessage(telegramUserBot,bot);
        }



    }
}
