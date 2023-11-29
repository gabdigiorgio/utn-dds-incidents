package org.utn.presentation.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;
import org.utn.application.LineManager;

public class LineController {
    private LineManager lineManager;
    private ObjectMapper objectMapper;

    public LineController(LineManager lineManager, ObjectMapper objectMapper) {
        this.lineManager = lineManager;
        this.objectMapper = objectMapper;
    }

    public Handler getLines = ctx -> {
        var lines = lineManager.getLines();

        String json = objectMapper.writeValueAsString(lines);

        ctx.json(json);
        ctx.status(200);
    };
}
