package org.utn.presentation.api.dto.responses;

import org.utn.domain.AccessibilityFeature;

public class AccessibilityFeatureResponse {
    private String catalogCode;
    private String status;
    private String type;
    private String station;
    private String line;
    private String dateSinceInaccessible;

    public AccessibilityFeatureResponse(AccessibilityFeature accessibilityFeature) {
        this.catalogCode = accessibilityFeature.getCatalogCode();
        this.status = accessibilityFeature.getStatus().toString();
        this.type = accessibilityFeature.getType().toString();
        this.station = accessibilityFeature.getStation().getName();
        this.line = accessibilityFeature.getStation().getLine().getName();
        if (accessibilityFeature.getDateSinceInaccessible() != null)
            this.dateSinceInaccessible = accessibilityFeature.getDateSinceInaccessible().toString();
    }

    public String getCatalogCode() {
        return catalogCode;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getStation() {
        return station;
    }

    public String getLine() {
        return line;
    }

    public String getDateSinceInaccessible() {
        return dateSinceInaccessible;
    }
}
