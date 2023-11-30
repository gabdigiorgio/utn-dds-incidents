package org.utn.persistence.incident;

import org.utn.domain.incident.Incident;
import org.utn.domain.incident.IncidentsRepository;

import java.util.*;
import java.util.stream.Collectors;

public final class IncidentsInMemoryRepository implements IncidentsRepository {
    public static List<Incident> incidents;

    public static IncidentsInMemoryRepository uniqueInstance;

    public IncidentsInMemoryRepository(List<Incident> incidents){
        IncidentsInMemoryRepository.incidents = incidents;
    }

    public static IncidentsInMemoryRepository getInstance(List<Incident> incidents) {
        if (uniqueInstance == null) {
            uniqueInstance = new IncidentsInMemoryRepository(incidents);
        }
        return uniqueInstance;
    }

    public static IncidentsInMemoryRepository getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new IncidentsInMemoryRepository(new ArrayList<>());
        }
        return uniqueInstance;
    }

    public void save(Incident incident) {
        Integer lastId = incidents.stream().mapToInt(i -> i.getId()).max().orElse(0);
        incident.setId(lastId + 1);
        incidents.add(incident);
    }

    public void update(Incident incident) {}

    @Override
    public void remove(Incident incident) {
        incidents.remove(incident);
    }

    public Incident getById(Integer id) {
        return this.incidents.get(id);
    }

    public List<Incident> findIncidents(int quantity, String state, String orderBy, String catalogCode) {
        List<Incident> list = incidents;
        if (state != null) {
            list = list.stream().filter(i -> i.getState().toString().equals(state)).collect(Collectors.toList());
        }
        if (catalogCode != null) {
            list = list.stream().filter(i -> i.getCatalogCode().equals(catalogCode)).collect(Collectors.toList());;
        }
        if (orderBy != null) {
            if (orderBy == "createdAt") list = getOrdered(list, true);
            else list = getOrdered(list, false);
        }
        return filterByQuantity(list, quantity);
    }

    @Override
    public List<Incident> findIncidentsWithPagination(int startIndex, int pageSize, String state, String orderBy, String catalogCode) {
        return null;
    }

    public int count() {
        return incidents.size();
    }

    public static List<Incident> filterByQuantity( List<Incident> list, int quantity) {
        if (list.size() <= quantity) {
            return list;
        } else {
            return list.subList(0, quantity);
        }
    }

    public static List<Incident> getOrdered(List<Incident> incidents, Boolean newsFirst) {
        Collections.sort(incidents, Comparator.comparing(Incident::getReportDate));
        if (newsFirst) Collections.reverse(incidents);
        return incidents;
    }
}
