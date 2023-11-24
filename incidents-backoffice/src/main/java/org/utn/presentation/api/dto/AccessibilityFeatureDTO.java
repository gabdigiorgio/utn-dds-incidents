package org.utn.presentation.api.dto;

public class AccessibilityFeatureDTO {
    private String catalogCode;
    private String type;
    private String status;
    private StationDTO station;

    public String getCatalogCode() {
        return catalogCode;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public StationDTO getStation() {
        return station;
    }
}
