package org.utn.domain.incident;

import org.utn.domain.incident.Incident;
import org.utn.domain.incident.state.State;

import java.util.List;

public interface IncidentsRepository {
    void save(Incident incident);

    void update(Incident incident);

    void remove(Incident incident);

    Incident getById(Integer id);

    List<Incident> findIncidents(int quantity, String state, String orderBy, String catalogCode);

    List<Incident> findIncidentsWithPagination(int startIndex, int pageSize, State state, String orderBy, String catalogCode);

    int count();

    boolean allIncidentsResolved(String catalogCode);
}
