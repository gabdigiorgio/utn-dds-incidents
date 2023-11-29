package org.utn.application;

import org.utn.domain.Line;
import org.utn.domain.Station;
import org.utn.persistence.line.LineRepository;
import org.utn.persistence.station.StationRepository;

import java.util.List;

public class StationManager {
    private final StationRepository stationRepository;

    public StationManager(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public List<Station> getStationFromLine(String lineId) {
        var stations = stationRepository.findByLineId(lineId);
        return stations;
    }
}
