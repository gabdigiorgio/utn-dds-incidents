package org.utn.persistence;

import org.utn.domain.incident.Incident;

import java.util.List;

public interface IncidentsRepository {
    void save(Incident incident);

    void update(Incident incident);

    void remove(Integer id);

    Incident getById(Integer id);

    List<Incident> findIncidents(int quantity, String state, String orderBy, String catalogCode);

    int count();
}