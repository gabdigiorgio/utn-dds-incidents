package org.utn.presentation.api.dto.responses;

public class AccessibilityFeatureResponse {
    private String catalogCode;
    private String type;
    private String status;
    private String station;
    private String line;

    public String getCatalogCode() {
        return catalogCode;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getStation() {
        return station;
    }

    public String getLine() {
        return line;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
