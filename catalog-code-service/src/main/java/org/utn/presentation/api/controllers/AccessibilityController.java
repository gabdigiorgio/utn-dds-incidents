package org.utn.presentation.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;
import org.utn.application.AccessibilityFeatureManager;
import org.utn.domain.AccessibilityFeature;

import java.util.List;
import java.util.Objects;

public class AccessibilityController {
    private AccessibilityFeatureManager accessibilityFeatureManager;
    private ObjectMapper objectMapper;

    public AccessibilityController(AccessibilityFeatureManager accessibilityFeatureManager, ObjectMapper objectMapper) {
        this.accessibilityFeatureManager = accessibilityFeatureManager;
        this.objectMapper = objectMapper;
    }

    public Handler getAccessibilityFeatures = ctx -> {
        Integer limit = ctx.queryParamAsClass("limit", Integer.class).getOrDefault(10);
        String catalogCode = ctx.queryParamAsClass("catalogCode", String.class).getOrDefault(null);
        String line = ctx.queryParamAsClass("line", String.class).getOrDefault(null);
        String station = ctx.queryParamAsClass("station", String.class).getOrDefault(null);
        String status = ctx.queryParamAsClass("status", String.class).getOrDefault(null);
        String type = ctx.queryParamAsClass("status", String.class).getOrDefault(null);

        var incidents = accessibilityFeatureManager.getAccessibilityFeatures(limit, catalogCode, line, station, status, type);

        String json = objectMapper.writeValueAsString(incidents);
        ctx.json(json);
        ctx.status(200);
    };

    public Handler getAccessibilityFeature = ctx -> {
        try {
            String catalogCode = Objects.requireNonNull(ctx.pathParam("catalogCode"));

            AccessibilityFeature accessibilityFeature = accessibilityFeatureManager.getAccessibilityFeature(catalogCode);

            if (accessibilityFeature != null) {
                String json = objectMapper.writeValueAsString(accessibilityFeature);
                ctx.json(json);
                ctx.status(200);
            } else {
                ctx.status(404).result("Accessibility Feature not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Internal Server Error");
        }
    };
}
