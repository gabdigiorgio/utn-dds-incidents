package org.utn.presentation.url_mappings;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import org.utn.presentation.api.controllers.LineController;

public class LineResource implements EndpointGroup {
    ObjectMapper objectMapper;

    public LineResource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void addEndpoints() {
        LineController lineController = new LineController(objectMapper);
        ApiBuilder.path("/api/lines", () -> {
            ApiBuilder.get("/", lineController.getLines);
            ApiBuilder.get("/{id}", lineController.getLine);
            ApiBuilder.get("/{id}/stations", lineController.getStationsFromLine);
        });
    }
}
