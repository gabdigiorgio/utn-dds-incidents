package org.utn.persistence.station;

import org.utn.domain.Station;

import java.util.List;

public interface StationRepository {
    Station getById(String id);
    List<Station> findByLineId(String lineId);
}
