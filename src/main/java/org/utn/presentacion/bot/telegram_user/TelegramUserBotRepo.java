package org.utn.presentacion.bot.telegram_user;

import java.util.ArrayList;
import java.util.List;

public class TelegramUserBotRepo implements UserBotRepo {
    private static TelegramUserBotRepo uniqueInstance;
    private TelegramUserBotRepo() {}

    public static TelegramUserBotRepo getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new TelegramUserBotRepo();
        }
        return uniqueInstance;
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
