package org.utn.presentacion.bot.telegram_user;

import org.utn.TelegramBot;
import org.utn.presentacion.bot.telegram_user_estado.UserBotStatus;

public class TelegramUserBot {
    private final Long id;
    private int messagesQuantity;
    private int incidentsQuantity;
    private UserBotStatus status;

    public UserBotStatus getStatus() {
        return status;
    }

    public void setStatus(UserBotStatus status) {
        this.status = status;
    }

    public TelegramUserBot(Long id, UserBotStatus status) {
        this.id = id;
        this.status = status;
        this.messagesQuantity = 0;
    }

    public Long getId() {
        return id;
    }

    public void addMessage(){
        messagesQuantity++;
    }
    public int getMessagesQuantity(){
        return messagesQuantity;
    }
    public int getIncidentsQuantity() {
        return incidentsQuantity;
    }

    public void setIncidentsQuantity(int incidentsQuantity) {
        this.incidentsQuantity = incidentsQuantity;
    }

    public void execute(String msg, TelegramBot bot) throws Exception {
        this.status.execute(this,msg,bot);
    }

}
