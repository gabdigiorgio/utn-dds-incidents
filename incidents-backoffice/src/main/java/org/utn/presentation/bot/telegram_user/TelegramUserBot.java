package org.utn.presentation.bot.telegram_user;

import org.utn.TelegramBot;
import org.utn.presentation.api.dto.responses.LineResponse;
import org.utn.presentation.api.dto.responses.StationResponse;
import org.utn.presentation.bot.telegram_user_state.UserBotState;

import java.util.List;

public class TelegramUserBot {
    private final Long id;
    private int messageCount;
    private int incidentsQuantity;
    private int inaccessibleAccessibilityFeaturesQuantity;
    private String line;
    private List<LineResponse> possibleLines;
    private List<StationResponse> possibleStations;
    private int lineIndex;
    private String station;
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

    public int getInaccessibleAccessibilityFeaturesQuantity() {
        return inaccessibleAccessibilityFeaturesQuantity;
    }

    public void setInaccessibleAccessibilityFeaturesQuantity(int inaccessibleAccessibilityFeaturesQuantity) {
        this.inaccessibleAccessibilityFeaturesQuantity = inaccessibleAccessibilityFeaturesQuantity;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getLine() {
        return line;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(int lineIndex) {
        this.lineIndex = lineIndex;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public List<LineResponse> getPossibleLines() {
        return possibleLines;
    }

    public void setPossibleLines(List<LineResponse> possibleLines) {
        this.possibleLines = possibleLines;
    }

    public List<StationResponse> getPossibleStations() {
        return possibleStations;
    }

    public void setPossibleStations(List<StationResponse> possibleStations) {
        this.possibleStations = possibleStations;
    }
}
