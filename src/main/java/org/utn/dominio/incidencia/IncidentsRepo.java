package org.utn.dominio.incidencia;

import java.util.List;

public interface IncidentsRepo {
    void save(Incident incident);

    void update(Incident incident);

    void remove(Integer id);

    Incident getIncidentById(Integer id);

    List<Incident> findIncidents(int quantity, String status, String orderBy, String place);

    int count();
}
