package org.utn.presentation.bot.telegram_user;

import org.utn.TelegramBot;
import org.utn.presentation.bot.telegram_user_state.UserBotState;

public class TelegramUserBot {
    private final Long id;
    private int messageCount;
    private int incidentsQuantity;
    private UserBotState state;

    public UserBotState getState() {
        return state;
    }

    public void setState(UserBotState state) {
        this.state = state;
    }

    public TelegramUserBot(Long id, UserBotState state) {
        this.id = id;
        this.state = state;
        this.messageCount = 0;
    }

    public Long getId() {
        return id;
    }

    public void addMessage(){
        messageCount++;
    }
    public int getMessageCount(){
        return messageCount;
    }
    public int getIncidentsQuantity() {
        return incidentsQuantity;
    }

    public void setIncidentsQuantity(int incidentsQuantity) {
        this.incidentsQuantity = incidentsQuantity;
    }

    public void execute(String msg, TelegramBot bot) throws Exception {
        this.state.execute(this,msg,bot);
    }

}
