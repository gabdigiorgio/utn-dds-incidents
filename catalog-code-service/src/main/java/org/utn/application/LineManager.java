package org.utn.application;

import org.utn.domain.Line;
import org.utn.domain.Station;
import org.utn.domain.LineRepository;

import java.util.List;

public class LineManager {
    private final LineRepository lineRepository;

    public LineManager(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }

    public Line getLine(String id) {
        return lineRepository.getById(id);
    }

    public List<Line> getLines() {
        var lines = lineRepository.all();
        return lines;
    }

    public List<Station> getStationsByLineId(String id) {
        return lineRepository.getById(id).getStations();
    }
}
