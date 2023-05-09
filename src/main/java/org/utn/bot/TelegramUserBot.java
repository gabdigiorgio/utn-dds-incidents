package org.utn.bot;

public class TelegramUserBot {
    private Long id;
    private TelegramUserBotState status;
    private int cantMensajes;

    public TelegramUserBot(Long id, TelegramUserBotState status) {
        this.id = id;
        this.status = status;
        this.cantMensajes = 0;
    }

    public Long getId() {
        return id;
    }

    public void addMensaje(){
        cantMensajes ++;
    }
    public int getCantMensajes(){
        return cantMensajes;
    }

    public void setStatus(TelegramUserBotState state) {
        status = state;
    }

    public TelegramUserBotState getStatus() {
        return status;
    }
}
