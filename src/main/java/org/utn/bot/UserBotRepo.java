package org.utn.bot;

public interface UserBotRepo {
    void save(TelegramUserBot bot);
    TelegramUserBot getById(Long id);
}
