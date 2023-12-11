package org.utn.domain.incident;

import org.utn.domain.incident.state.State;
import org.utn.domain.users.User;

import java.util.List;

public interface IncidentsRepository {
    void save(Incident incident);

    void update(Incident incident);

    void remove(Incident incident);

    Incident getById(Integer id);

    List<Incident> findIncidents(int quantity, String state, String orderBy, String catalogCode);

    Incidents findIncidentsWithPagination(Integer page, Integer pageSize, State state, String orderBy, String catalogCode, User reporter);

    int count();

    boolean allIncidentsResolved(String catalogCode);
}
