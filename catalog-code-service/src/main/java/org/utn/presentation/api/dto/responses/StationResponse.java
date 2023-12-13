package org.utn.presentation.api.dto.responses;

import org.utn.domain.Station;

public class StationResponse {
    private int id;
    private String name;
    private String lineConnection;

    public StationResponse(Station station) {
        this.id = station.getId();
        this.name = station.getName();
        if (station.getConnection() != null) this.lineConnection = station.getConnection().getLine().getName();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLineConnection() {
        return lineConnection;
    }
}
