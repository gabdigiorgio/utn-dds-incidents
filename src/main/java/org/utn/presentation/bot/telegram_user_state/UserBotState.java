package org.utn.presentation.bot.telegram_user_state;

import org.utn.TelegramBot;
import org.utn.presentation.bot.telegram_user.TelegramUserBot;

public abstract class UserBotState {
    SubState subState = SubState.START;

    public UserBotState() {}

    public SubState getSubState() {
        return subState;
    }
    public void setSubState(SubState subState) {
        this.subState = subState;
    }

    public abstract String getStateName();
    public abstract void execute(TelegramUserBot telegramUserBot, String messageText, TelegramBot bot) throws Exception;

}
