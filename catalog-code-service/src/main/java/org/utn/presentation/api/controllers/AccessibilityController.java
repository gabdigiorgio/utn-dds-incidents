package org.utn.presentation.api.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import org.utn.domain.AccessibilityFeature;
import org.utn.modules.ManagerFactory;
import org.utn.presentation.api.dto.requests.StatusRequest;
import org.utn.presentation.api.dto.responses.AccessibilityFeatureResponse;
import org.utn.presentation.api.dto.responses.ErrorResponse;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AccessibilityController {
    private ObjectMapper objectMapper;

    public AccessibilityController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Handler getAccessibilityFeature = ctx -> {
        var accessibilityFeatureManager = ManagerFactory.createAccessibilityFeatureManager();

        String catalogCode = Objects.requireNonNull(ctx.pathParam("catalogCode"));

        var accessibilityFeature = accessibilityFeatureManager.getAccessibilityFeature(catalogCode);
        var accessibilityFeatureResponse = new AccessibilityFeatureResponse(accessibilityFeature);

        returnJson(objectMapper.writeValueAsString(accessibilityFeatureResponse), ctx);
    };

    public Handler getAccessibilityFeatures = ctx -> {
        var accessibilityFeatureManager = ManagerFactory.createAccessibilityFeatureManager();

        Integer limit = ctx.queryParamAsClass("limit", Integer.class).getOrDefault(null);
        String catalogCode = ctx.queryParamAsClass("catalogCode", String.class).getOrDefault(null);
        String line = ctx.queryParamAsClass("line", String.class).getOrDefault(null);
        String station = ctx.queryParamAsClass("station", String.class).getOrDefault(null);

        AccessibilityFeature.Status status = null;
        String statusParam = ctx.queryParam("status");
        if (statusParam != null) {
            status = AccessibilityFeature.Status.valueOf(statusParam.toUpperCase());
        }

        AccessibilityFeature.Type type = null;
        String typeParam = ctx.queryParam("type");
        if (typeParam != null) {
            type = AccessibilityFeature.Type.valueOf(typeParam.toUpperCase());
        }

        var accessibilityFeatures = accessibilityFeatureManager.getAccessibilityFeatures(limit, catalogCode, line, station, status, type);
        var accessibilityFeaturesResponse = mapAccessibilityFeatureResponses(accessibilityFeatures);

        returnJson(objectMapper.writeValueAsString(accessibilityFeaturesResponse), ctx);
    };

    public Handler updateAccessibilityFeature = ctx -> {
        var accessibilityFeatureManager = ManagerFactory.createAccessibilityFeatureManager();

        String catalogCode = Objects.requireNonNull(ctx.pathParam("catalogCode"));
        var statusRequest = ctx.bodyAsClass(StatusRequest.class);
        var statusStr = statusRequest.getStatus();
        var upperCaseStatus = statusStr.toUpperCase();

        AccessibilityFeature.Status status = AccessibilityFeature.Status.valueOf(upperCaseStatus);

        var accessibilityFeature = accessibilityFeatureManager.updateAccessibilityFeatureStatus(catalogCode, status);
        var accessibilityFeatureResponse = new AccessibilityFeatureResponse(accessibilityFeature);

        returnJson(objectMapper.writeValueAsString(accessibilityFeatureResponse), ctx);
    };

    private void returnJson(String objectMapper, Context ctx) {
        String json = objectMapper;
        ctx.json(json);
    }

    @NotNull
    private static List<AccessibilityFeatureResponse> mapAccessibilityFeatureResponses(List<AccessibilityFeature> accessibilityFeatures) {
        return accessibilityFeatures.stream()
                .map(AccessibilityFeatureResponse::new)
                .collect(Collectors.toList());
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
