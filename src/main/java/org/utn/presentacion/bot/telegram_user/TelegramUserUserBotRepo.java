package org.utn.presentacion.bot.telegram_user;

import java.util.ArrayList;
import java.util.List;

public class TelegramUserUserBotRepo implements UserBotRepo {
    private static TelegramUserUserBotRepo instanciaUnica;
    private TelegramUserUserBotRepo() {}

    public static TelegramUserUserBotRepo obtenerInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new TelegramUserUserBotRepo();
        }
        return instanciaUnica;
    }
    private List<TelegramUserBot> telegramBots = new ArrayList<>();
    public void save(TelegramUserBot bot) {
        telegramBots.add(bot);
    }

    public TelegramUserBot getById(Long id) {
        return telegramBots.stream()
                .filter(telegramBot -> telegramBot.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

}
