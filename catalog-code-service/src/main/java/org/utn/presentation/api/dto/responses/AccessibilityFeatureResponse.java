package org.utn.presentation.api.dto.responses;

import org.utn.domain.AccessibilityFeature;

public class AccessibilityFeatureResponse {
    private String catalogCode;
    private String status;
    private String type;

    public AccessibilityFeatureResponse(AccessibilityFeature accessibilityFeature) {
        this.catalogCode = accessibilityFeature.getCatalogCode();
        this.status = accessibilityFeature.getStatus().toString();
        this.type = accessibilityFeature.getType().toString();
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
}
