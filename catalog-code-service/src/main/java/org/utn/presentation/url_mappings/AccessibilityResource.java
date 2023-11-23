package org.utn.presentation.url_mappings;

import io.javalin.apibuilder.ApiBuilder;
import org.utn.application.AccessibilityFeatureManager;

public class AccessibilityResource {
    AccessibilityFeatureManager accessibilityFeatureManager;

    public AccessibilityResource(AccessibilityFeatureManager accessibilityFeatureManager) {
        this.accessibilityFeatureManager = accessibilityFeatureManager;
    }

    /*@Override
    public void addEndpoints() {
        AccessibilityController accessibilityController = new AccessibilityController(accessibilityFeatureManager, objectMapper);
        ApiBuilder.path("/api/accessibilityFeature", () -> {
            ApiBuilder.get("/{catalogCode}", accessibilityController.getAccessibilityFeature);
        });*/
}

