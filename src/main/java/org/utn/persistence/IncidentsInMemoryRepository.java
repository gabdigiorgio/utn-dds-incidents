package org.utn.persistence;

import org.utn.domain.incident.Incident;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public void remove(Integer id) {
        OptionalInt incidentToRemove = IntStream.range(0, incidents.size()).filter(i -> incidents.get(i).getId().equals(id)).findFirst();
        if (incidentToRemove.isPresent()) incidents.remove(incidentToRemove.getAsInt());
    }

    public Incident getById(Integer id) {
        List<Incident> result = incidents.stream().filter(i -> i.getId().equals(id)).collect(Collectors.toList());
        if (result.size() != 1) return null;
        return result.get(0);
    }

    public List<Incident> findIncidents(int quantity, String state, String orderBy, String catalogCode) {
        List<Incident> list = incidents;
        if (state != null) {
            list = list.stream().filter(i -> i.getStateName().equals(state)).collect(Collectors.toList());
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
        Collections.sort(incidents, (unaIncidencia, otra) -> unaIncidencia.getReportDate().compareTo(otra.getReportDate()));
        if (newsFirst) Collections.reverse(incidents);
        return incidents;
    }
}
