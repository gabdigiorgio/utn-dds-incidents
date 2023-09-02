package org.utn;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.utn.presentation.bot.telegram_user.TelegramUserBot;

import org.utn.presentation.bot.telegram_user.TelegramUserUserBotRepo;
import org.utn.presentation.bot.telegram_user.UserBotRepo;
import org.utn.presentation.bot.telegram_user_state.WelcomeChat;
import org.utn.presentation.incidents_load.CsvReader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelegramBot extends TelegramLongPollingBot {
    private static boolean isBotStarted = false;

    public TelegramBot(String botToken) {
        super(botToken);
    }

    public static boolean isBotStarted() {
        return isBotStarted;
    }

    public static void setBotStarted(boolean started) {
        isBotStarted = started;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();

        UserBotRepo repoTelegramBots = TelegramUserUserBotRepo.getInstance();
        TelegramUserBot telegramUserBot = repoTelegramBots.getById(chatId);
        if (telegramUserBot == null) {
            telegramUserBot = new TelegramUserBot(chatId, new WelcomeChat());
            repoTelegramBots.save(telegramUserBot);
        }

        telegramUserBot.addMessage();

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();

            logger(message,telegramUserBot);
            try {
                //TODO Esto es solo para testear despues lo borramos
                if (messageText.equals("/state")){
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(telegramUserBot.getId());
                    String msg = String.format("El usuario se tiene el chat id: %d\n",telegramUserBot.getId())
                            + String.format("Este es el mensaje #%d\n",telegramUserBot.getMessageCount())
                            + String.format("El user se encuentra en el estado [%s]",telegramUserBot.getState().getStateName());
                    sendMessage.setText(msg);
                    execute(sendMessage);
                } else {
                    telegramUserBot.execute(messageText,this);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (message.hasDocument()) {
            try {
                // Obtiene el archivo
                GetFile getFile = new GetFile();
                getFile.setFileId(message.getDocument().getFileId());
                org.telegram.telegrambots.meta.api.objects.File file = execute(getFile);
                java.io.File downloadedFile = downloadFile(file);

                String filePath = downloadedFile.getAbsolutePath();
                String result = new CsvReader().execute(filePath);

                // Envia el mensaje al usuario
                SendMessage responseMsg = new SendMessage();
                responseMsg.setChatId(message.getChatId());
                responseMsg.setText(result);
                execute(responseMsg);
            } catch (Exception e) {
                e.printStackTrace();
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

            setBotStarted(true);

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
        String userState = userBot.getState().getStateName();
        String userSubState = userBot.getState().getSubState().toString();
        log(userFirstName, userLastName, Long.toString(userId), messageText,userState,userSubState);
    }

    private void log(String firstName, String lastName, String userId, String txt,String userState,String userSubState) {
        System.out.println("----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + firstName + " " + lastName + ". (id = " + userId + ") \n Text - " + txt);
        System.out.println("User in state: " + userState);
        System.out.println("User in sub_state: " + userSubState);
    }
}
