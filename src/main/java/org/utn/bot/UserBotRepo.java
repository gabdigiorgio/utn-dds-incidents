package org.utn.bot;

import java.util.Optional;

public interface UserBotRepo {
    void save(TelegramUserBot bot);
    Optional<TelegramUserBot> getById(Long id);
}
