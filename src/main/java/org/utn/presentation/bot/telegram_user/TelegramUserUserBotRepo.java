package org.utn.presentation.bot.telegram_user;

import java.util.ArrayList;
import java.util.List;

public class TelegramUserUserBotRepo implements UserBotRepo {
    private static TelegramUserUserBotRepo uniqueInstance;

    private TelegramUserUserBotRepo() {
    }

    public static TelegramUserUserBotRepo getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new TelegramUserUserBotRepo();
        }
        return uniqueInstance;
    }

    private final List<TelegramUserBot> telegramBots = new ArrayList<>();

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
