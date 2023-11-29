package org.utn.presentation.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;
import org.utn.application.LineManager;

import java.util.Objects;

public class LineController {
    private LineManager lineManager;
    private ObjectMapper objectMapper;

    public LineController(LineManager lineManager, ObjectMapper objectMapper) {
        this.lineManager = lineManager;
        this.objectMapper = objectMapper;
    }

    public Handler getLines = ctx -> {
        var lines = lineManager.getLines();

        var json = objectMapper.writeValueAsString(lines);

        ctx.json(json);
        ctx.status(200);
    };

    public Handler getLine = ctx -> {
        var id = Objects.requireNonNull(ctx.pathParam("id"));

        var line = lineManager.getLine(id);
        var json = objectMapper.writeValueAsString(line);

        ctx.json(json);
        ctx.status(200);
    };
}
