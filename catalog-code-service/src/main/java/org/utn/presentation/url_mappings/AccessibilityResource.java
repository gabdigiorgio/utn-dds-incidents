package org.utn.presentation.url_mappings;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import org.utn.application.AccessibilityFeatureManager;
import org.utn.presentation.api.controllers.AccessibilityController;

public class AccessibilityResource implements EndpointGroup {
    AccessibilityFeatureManager accessibilityFeatureManager;
    ObjectMapper objectMapper;

    public AccessibilityResource(AccessibilityFeatureManager accessibilityFeatureManager, ObjectMapper objectMapper) {
        this.accessibilityFeatureManager = accessibilityFeatureManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public void addEndpoints() {
        AccessibilityController accessibilityController = new AccessibilityController(accessibilityFeatureManager, objectMapper);
        ApiBuilder.path("/api/accessibilityFeatures", () -> {
            ApiBuilder.get("/", accessibilityController.getAccessibilityFeatures);
            ApiBuilder.put("/{catalogCode}", accessibilityController.updateAccessibilityFeature);
            ApiBuilder.get("/{catalogCode}", accessibilityController.getAccessibilityFeature);
        });
    }
}

