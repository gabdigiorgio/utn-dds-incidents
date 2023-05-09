package org.utn.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelegramBot extends TelegramLongPollingBot {

    public TelegramBot(String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();

        TelegramUserUserBotRepo repoTelegramBots = TelegramUserUserBotRepo.obtenerInstancia();
        TelegramUserBot telegramUserBot = repoTelegramBots.getById(chatId);
        if (telegramUserBot == null) {
            telegramUserBot = new TelegramUserBot(chatId, TelegramUserBotState.WELCOME_CHAT);
            repoTelegramBots.save(telegramUserBot);
        }

        telegramUserBot.addMensaje();

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            logger(message,telegramUserBot);
            try {
                //TODO Esto es solo para testear despues lo borramos
                if (messageText.equals("/state")){
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(telegramUserBot.getId());
                    String msg = String.format("El usuario se tiene el chat id: %d\n",telegramUserBot.getId())
                            + String.format("Este es el mensaje #%d\n",telegramUserBot.getCantMensajes())
                            + String.format("El user se encuentra en el estado [%s]",telegramUserBot.getStatus().toString());
                    sendMessage.setText(msg);
                    execute(sendMessage);
                } else {

                    TelegramHandler.handleUserState(telegramUserBot,messageText,this);
                }

            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }


        }
    }

    public static void main(String[] args) throws TelegramApiException {

        // Se crea un nuevo Bot API
        final TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        try {
            // Se devuelve el token que nos generó el BotFather de nuestro bot
            String tokenbot = System.getenv("TELEGRAM_BOT_TOKEN");
            // Se registra el bot
            telegramBotsApi.registerBot(new TelegramBot(tokenbot));
            System.out.println("Se inicio la ejecución del BOT correctamente.");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getBotUsername() {
        // Se devuelve el nombre que dimos al bot al crearlo con el BotFather
        return System.getenv("TELEGRAM_BOT_NAME");
    }

    private void logger(Message message,TelegramUserBot userBot){
        String userFirstName = message.getChat().getFirstName();
        String userLastName = message.getChat().getLastName();
        long userId = message.getChat().getId();
        String messageText = message.getText();
        String userState = userBot.getStatus().toString();

        log(userFirstName, userLastName, Long.toString(userId), messageText,userState);
    }

    private void log(String firstName, String lastName, String userId, String txt,String userState) {
        System.out.println("----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + firstName + " " + lastName + ". (id = " + userId + ") \n Text - " + txt);
        System.out.println("User in state: " + userState);
        //System.out.println("Bot answer: \n Text - " + bot_answer);
    }

}
