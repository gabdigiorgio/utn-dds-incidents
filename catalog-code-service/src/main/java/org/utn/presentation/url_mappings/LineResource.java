package org.utn.presentation.url_mappings;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import org.utn.application.AccessibilityFeatureManager;
import org.utn.application.LineManager;
import org.utn.domain.Line;
import org.utn.presentation.api.controllers.AccessibilityController;
import org.utn.presentation.api.controllers.LineController;

public class LineResource implements EndpointGroup {
    LineManager lineManager;
    ObjectMapper objectMapper;

    public LineResource(LineManager lineManager, ObjectMapper objectMapper) {
        this.lineManager = lineManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public void addEndpoints() {
        LineController lineController = new LineController(lineManager, objectMapper);
        ApiBuilder.path("/api/lines", () -> {
            ApiBuilder.get("/", lineController.getLines);
        });
    }
}
