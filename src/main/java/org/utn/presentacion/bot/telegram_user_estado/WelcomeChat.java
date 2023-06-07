package org.utn.presentacion.bot.telegram_user_estado;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.presentacion.bot.telegram_user.TelegramUserBot;

import static org.utn.presentacion.bot.Shows.*;

public class WelcomeChat extends UserBotEstado {

    public WelcomeChat() {}
    @Override
    public String getNombreEstado() {
        return "WelcomeChat";
    }

    @Override
    public void execute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        switch (subEstado){
            case START -> startExecute(telegramUserBot,bot);
            case WAITING_RESPONSE_OPTION -> waitingResponseExecute(telegramUserBot,messageText,bot);
        }
    }

    private void startExecute(TelegramUserBot telegramUserBot, TelegramBot bot) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(telegramUserBot.getId());
        String msg = "¡Hola! \uD83D\uDE00 Bienvenido al bot de TPA SAMA - GRUPO 1";
        sendMessage.setText(msg);
        bot.execute(sendMessage);

        msg = "✏️ Escribe \"/menu\" para ver las opciones disponibles ";
        sendMessage.setText(msg);
        bot.execute(sendMessage);
        this.setSubEstado(SubEstado.WAITING_RESPONSE_OPTION);
    }

    private void waitingResponseExecute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception {
        if (messageText.equals("/menu")) {
            telegramUserBot.setEstado(new MainMenu());
            telegramUserBot.execute(messageText,bot);
        } else{
            invalidMessage(telegramUserBot,bot);
            showInitMessage(telegramUserBot,bot);
        }

    }

}
