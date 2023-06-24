package org.utn.presentacion.bot.telegram_user_estado;

import org.utn.TelegramBot;
import org.utn.presentacion.bot.telegram_user.TelegramUserBot;

public abstract class UserBotStatus {
    SubStatus subStatus = SubStatus.START;

    public UserBotStatus() {}

    public SubStatus getSubStatus() {
        return subStatus;
    }
    public void setSubStatus(SubStatus subStatus) {
        this.subStatus = subStatus;
    }

    public abstract String getStatusName();
    public abstract void execute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception;

}
