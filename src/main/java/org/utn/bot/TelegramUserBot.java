package org.utn.bot;

public class TelegramUserBot {
    public TelegramUserBot(Long id, Integer status) {
        this.id = id;
        this.status = status;
    }

    private Long id;
    private Integer status;

    public Long getId() {
        return id;
    }
    public Integer getStatus() {
        return status;
    }
}
