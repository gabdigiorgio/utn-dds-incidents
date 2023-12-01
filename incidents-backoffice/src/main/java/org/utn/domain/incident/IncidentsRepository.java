package org.utn.domain.incident;

import org.utn.domain.incident.Incident;

import java.util.List;

public interface IncidentsRepository {
    void save(Incident incident);

    void update(Incident incident);

    void remove(Incident incident);

    Incident getById(Integer id);

    List<Incident> findIncidents(int quantity, String state, String orderBy, String catalogCode);

    List<Incident> findIncidentsWithPagination(int startIndex, int pageSize, String state, String orderBy, String catalogCode);

    int count();
}
