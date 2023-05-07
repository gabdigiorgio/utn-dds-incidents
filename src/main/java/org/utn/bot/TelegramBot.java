package org.utn.bot;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

//java -cp target/copiame.jarwith dependencies
public class TelegramBot extends TelegramLongPollingBot {

    public TelegramBot(String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();

        TelegramUserUserBotRepo repoTelegramBots = TelegramUserUserBotRepo.obtenerInstancia();
        Optional<TelegramUserBot> telegramUserBot = repoTelegramBots.getById(chatId);
        if (telegramUserBot == null) {
            TelegramUserBot newTelegramUserBot = new TelegramUserBot(chatId, 1);
            repoTelegramBots.save(newTelegramUserBot);
        }

        SendMessage responseMsg = new SendMessage();
        responseMsg.setChatId(message.getChatId());
        responseMsg.setText("" +
                "*Asistente virtual para consulta de incidentes reportados* \n\n" +
                "Por favor seleccione una opción: \r\n" +
                "1) Obtener listado de incidencias \n" +
                "2) Indicar lugar de incidencias requeridas \n");
        responseMsg.setParseMode("Markdown");

        try {
            execute(responseMsg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    private String sendRequest(java.io.File downloadedFile) throws IOException, ClientProtocolException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(
                "https://dds-copiame-webs.onrender.com/analisis");
        MultipartEntityBuilder builder =
                MultipartEntityBuilder.create();
        builder.addBinaryBody( "file", downloadedFile,
                ContentType.DEFAULT_BINARY, "data.zip");
        HttpEntity multipart = builder.build();
        httpPost.setEntity(multipart);
        HttpResponse execute = httpClient.execute(httpPost);
        String rta = IOUtils.toString(
                execute.getEntity().getContent(),
                StandardCharsets.UTF_8.name());
        return rta;
    }

    @Override
    public String getBotUsername() {
        // Se devuelve el nombre que dimos al bot al crearlo con el BotFather
        return System.getenv("TELEGRAM_BOT_NAME");
    }

    public static void main(String[] args) throws TelegramApiException {

        // Se crea un nuevo Bot API
        final TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

        try {
            // Se devuelve el token que nos generó el BotFather de nuestro bot
            String tokenbot = System.getenv("TELEGRAM_BOT_TOKEN");
            // Se registra el bot
            telegramBotsApi.registerBot(new TelegramBot(tokenbot));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
