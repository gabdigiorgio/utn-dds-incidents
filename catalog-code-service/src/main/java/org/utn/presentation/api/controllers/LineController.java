package org.utn.presentation.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import org.utn.domain.Line;
import org.utn.domain.Station;
import org.utn.modules.ManagerFactory;
import org.utn.presentation.api.dto.responses.LineResponse;
import org.utn.presentation.api.dto.responses.StationResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LineController {
    private ObjectMapper objectMapper;

    public LineController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Handler getLine = ctx -> {
        var lineManager = ManagerFactory.createLineManager();

        var id = getId(ctx);
        var line = lineManager.getLine(id);

        LineResponse lineResponse = new LineResponse(line.getId(), line.getName());

        returnJson(objectMapper.writeValueAsString(lineResponse), ctx);
    };

    public Handler getLines = ctx -> {
        var lineManager = ManagerFactory.createLineManager();

        var lines = lineManager.getLines();

        List<LineResponse> lineResponses = mapLinesResponses(lines);

        returnJson(objectMapper.writeValueAsString(lineResponses), ctx);
    };

    public Handler getStationsFromLine = ctx -> {
        var lineManager = ManagerFactory.createLineManager();

        var id = getId(ctx);
        var stations = lineManager.getStationsByLineId(id);
        var stationResponses = mapStationsResponses(stations);

        returnJson(objectMapper.writeValueAsString(stationResponses), ctx);
    };

    private void returnJson(String objectMapper, Context ctx) {
        var json = objectMapper;
        ctx.json(json);
    }

    @NotNull
    private static String getId(Context ctx) {
        return Objects.requireNonNull(ctx.pathParam("id"));
    }

    @NotNull
    private static List<StationResponse> mapStationsResponses(List<Station> stations) {
        return stations.stream()
                .map(station -> {
                    var response = new StationResponse(station);
                    return response;
                })
                .collect(Collectors.toList());
    }

    @NotNull
    private static List<LineResponse> mapLinesResponses(List<Line> lines) {
        return lines.stream()
                .map(line -> new LineResponse(line.getId(), line.getName()))
                .collect(Collectors.toList());
    }
}
