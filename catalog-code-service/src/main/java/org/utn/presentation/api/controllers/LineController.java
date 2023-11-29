package org.utn.presentation.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;
import org.utn.modules.ManagerFactory;

import java.util.Objects;

public class LineController {
    private ObjectMapper objectMapper;

    public LineController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Handler getLines = ctx -> {
        var lineManager = ManagerFactory.createLineManager();

        var lines = lineManager.getLines();

        var json = objectMapper.writeValueAsString(lines);

        ctx.json(json);
        ctx.status(200);
    };

    public Handler getLine = ctx -> {
        var lineManager = ManagerFactory.createLineManager();

        var id = Objects.requireNonNull(ctx.pathParam("id"));

        var line = lineManager.getLine(id);
        var json = objectMapper.writeValueAsString(line);

        ctx.json(json);
        ctx.status(200);
    };
}
