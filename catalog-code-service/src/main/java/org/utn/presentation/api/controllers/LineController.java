package org.utn.presentation.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import org.utn.domain.Station;
import org.utn.modules.ManagerFactory;
import org.utn.presentation.api.dto.responses.StationResponse;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LineController {
    private ObjectMapper objectMapper;

    public LineController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Handler getLines = ctx -> {
        var lineManager = ManagerFactory.createLineManager();

        var lines = lineManager.getLines();

        returnJson(objectMapper.writeValueAsString(lines), ctx);
    };

    public Handler getLine = ctx -> {
        var lineManager = ManagerFactory.createLineManager();

        var id = getId(ctx);
        var line = lineManager.getLine(id);

        returnJson(objectMapper.writeValueAsString(line), ctx);
    };

    public Handler getStations = ctx -> {
        var stationManager = ManagerFactory.createStationManager();

        var id = getId(ctx);
        var stations = stationManager.getStationFromLine(id);
        var stationResponses = mapStationResponses(stations);

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
    private static List<StationResponse> mapStationResponses(List<Station> stations) {
        var stationResponses = stations.stream()
                .map(station -> {
                    var response = new StationResponse(station);
                    return response;
                })
                .collect(Collectors.toList());
        return stationResponses;
    }
}
