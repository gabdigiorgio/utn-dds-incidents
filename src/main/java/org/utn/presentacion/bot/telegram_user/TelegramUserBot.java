package org.utn.presentacion.bot.telegram_user;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.utn.TelegramBot;
import org.utn.presentacion.bot.telegram_user_estado.UserBotEstado;

public class TelegramUserBot {
    private final Long id;
    private int cantMensajes;
    private int incidentsQuantity;
    private UserBotEstado estado;

    public UserBotEstado getEstado() {
        return estado;
    }

    public void setEstado(UserBotEstado estado) {
        this.estado = estado;
    }

    public TelegramUserBot(Long id, UserBotEstado estado) {
        this.id = id;
        this.estado = estado;
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
    public int getIncidentsQuantity() {
        return incidentsQuantity;
    }

    public void setIncidentsQuantity(int incidentsQuantity) {
        this.incidentsQuantity = incidentsQuantity;
    }

    public void execute(String msg, TelegramBot bot) throws TelegramApiException {
        this.estado.execute(this,msg,bot);
    }

}
