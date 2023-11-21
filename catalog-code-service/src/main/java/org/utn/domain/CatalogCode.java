package org.utn.domain;

public class CatalogCode {
    private String code;
    private AccessibilityFeature accessibilityFeature;

    public CatalogCode(String code, AccessibilityFeature accessibilityFeature) {
        this.code = code;
        this.accessibilityFeature = accessibilityFeature;
    }

    public String getCode() { return code; }

    public AccessibilityFeature getAccessibilityFeature() {
        return accessibilityFeature;
    }
}
