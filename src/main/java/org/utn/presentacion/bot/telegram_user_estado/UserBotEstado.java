package org.utn.presentacion.bot.telegram_user_estado;

import org.utn.TelegramBot;
import org.utn.presentacion.bot.telegram_user.TelegramUserBot;

public abstract class UserBotEstado {
    SubEstado subEstado = SubEstado.START;

    public UserBotEstado() {}

    public SubEstado getSubEstado() {
        return subEstado;
    }
    public void setSubEstado(SubEstado subEstado) {
        this.subEstado = subEstado;
    }

    public abstract String getNombreEstado();
    public abstract void execute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception;

}
