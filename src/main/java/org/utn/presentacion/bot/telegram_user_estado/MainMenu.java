package org.utn.presentacion.bot.telegram_user_estado;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.aplicacion.IncidentManager;
import org.utn.persistencia.MemIncidentsRepo;
import org.utn.presentacion.bot.telegram_user.TelegramUserBot;

import static org.utn.presentacion.bot.Shows.*;

public class MainMenu extends UserBotStatus {

    static IncidentManager manager = new IncidentManager(MemIncidentsRepo.getInstance());

    public MainMenu() {}

    @Override
    public String getStatusName() {
        return "MainMenu";
    }

    @Override
    public void execute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        switch (subStatus){
            case START -> startExecute(telegramUserBot,bot);
            case WAITING_RESPONSE_OPTION -> waitingResponseExecute(telegramUserBot,messageText,bot);
        }
    }

    private void startExecute(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage response = new SendMessage();
        response.setChatId(telegramUserBot.getId());
        response.setText("""
                Escriba el número de la opción deseada:
                1️⃣ ☞ Obtener N incidents (La mas recientes primero)
                2️⃣ ☞ Obtener N incidents (La mas antigua primero)
                3️⃣ ☞ Obtener N incidents  filtrando por status
                4️⃣ ☞ Obtener las incidents de un codigo de catalogo""");
        try {
            bot.execute(response);
            this.setSubStatus(SubStatus.WAITING_RESPONSE_OPTION);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void waitingResponseExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());

        switch (messageText) {
            case "1" -> {
                telegramUserBot.setStatus(new GetIncidentsLastReport(manager));
                telegramUserBot.execute(messageText,bot);
            }
            case "2" -> {
                telegramUserBot.setStatus(new GetIncidentsFirstReport(manager));
                telegramUserBot.execute(messageText,bot);
            }
            case "3" -> {
                telegramUserBot.setStatus(new GetIncidentsByStatus(manager));
                telegramUserBot.execute(messageText,bot);
            }
            case "4" -> {
                telegramUserBot.setStatus(new GetIncidentsByPlace(manager));
                telegramUserBot.execute(messageText,bot);
            }
            default -> invalidMessage(telegramUserBot,bot);
        }



    }
}
