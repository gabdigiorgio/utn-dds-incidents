package org.utn.presentacion.bot.telegram_user;

import org.utn.presentacion.bot.telegram_user.TelegramUserBot;

public interface UserBotRepo {
    void save(TelegramUserBot bot);
    TelegramUserBot getById(Long id);
}
