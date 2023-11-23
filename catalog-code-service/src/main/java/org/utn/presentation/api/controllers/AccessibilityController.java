package org.utn.presentation.api.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.utn.application.AccessibilityFeatureManager;
import org.utn.domain.AccessibilityFeature;
import org.utn.presentation.api.dto.ErrorResponse;

import java.util.Collections;
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
        try {
            Integer limit = ctx.queryParamAsClass("limit", Integer.class).getOrDefault(10);
            String catalogCode = ctx.queryParamAsClass("catalogCode", String.class).getOrDefault(null);
            String line = ctx.queryParamAsClass("line", String.class).getOrDefault(null);
            String station = ctx.queryParamAsClass("station", String.class).getOrDefault(null);
            // Convertir status a AccessibilityFeature.Status
            AccessibilityFeature.Status status = null;
            String statusParam = ctx.queryParam("status");
            if (statusParam != null) {
                status = AccessibilityFeature.Status.valueOf(statusParam.toUpperCase());
            }

            // Convertir type a AccessibilityFeature.Type
            AccessibilityFeature.Type type = null;
            String typeParam = ctx.queryParam("type");
            if (typeParam != null) {
                type = AccessibilityFeature.Type.valueOf(typeParam.toUpperCase());
            }

            var incidents = accessibilityFeatureManager.getAccessibilityFeatures(limit, catalogCode, line, station, status, type);

            String json = objectMapper.writeValueAsString(incidents);
            ctx.json(json);
            ctx.status(200);
        } catch (IllegalArgumentException e) {
            handleBadRequest(ctx, e);
        } catch (Exception e) {
            handleInternalError(ctx, e);
        }
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
            handleInternalError(ctx, e);
        }
    };

    private void handleBadRequest(Context ctx, IllegalArgumentException e) throws JsonProcessingException {
        ctx.json(parseErrorResponse(400, e.getMessage()));
        ctx.status(400);
    }

    private void handleInternalError(Context ctx, Exception e) throws JsonProcessingException {
        ctx.json(parseErrorResponse(500, e.getMessage()));
        ctx.status(500);
    }

    public static String parseErrorResponse(int statusCode, String errorMsg) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.status = statusCode;
        errorResponse.message = errorMsg;
        errorResponse.errors = Collections.singletonList(errorMsg);

        return objectMapper.writeValueAsString(errorResponse);
    }
}
