package org.utn.bot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Optional<TelegramUserBot> getById(Long id) {
        return telegramBots.stream().filter(telegramBot -> telegramBot.getId().equals(id)).findFirst();
    }

}
