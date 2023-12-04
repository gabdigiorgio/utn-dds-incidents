package org.utn.presentation.api.dto.responses;

public class AccessibilityFeatureResponse {
    private String catalogCode;
    private String type;
    private String status;
    private String station;

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
}
