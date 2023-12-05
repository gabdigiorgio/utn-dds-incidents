package org.utn.infrastructure.responses;

public class AccessibilityFeatureInventoryResponse {
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
}
