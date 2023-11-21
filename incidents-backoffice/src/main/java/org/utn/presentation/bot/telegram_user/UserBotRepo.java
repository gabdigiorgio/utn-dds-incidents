package org.utn.presentation.bot.telegram_user;

public interface UserBotRepo {
    void save(TelegramUserBot bot);
    TelegramUserBot getById(Long id);
}
