package org.utn.presentation.url_mappings;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import org.utn.presentation.api.controllers.AccessibilityController;

public class AccessibilityResource implements EndpointGroup {
    ObjectMapper objectMapper;

    public AccessibilityResource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void addEndpoints() {
        AccessibilityController accessibilityController = new AccessibilityController(objectMapper);
        ApiBuilder.path("/api/accessibility-features", () -> {
            ApiBuilder.get("/", accessibilityController.getAccessibilityFeatures);
            ApiBuilder.put("/{catalogCode}", accessibilityController.updateAccessibilityFeature);
            ApiBuilder.get("/{catalogCode}", accessibilityController.getAccessibilityFeature);
        });
    }
}

