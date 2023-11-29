package org.utn.presentation.api.dto.responses;


import org.utn.domain.Station;

public class StationResponse {
    private int id;
    private String name;

    public StationResponse(Station station) {
        this.id = station.getId();
        this.name = station.getName();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
