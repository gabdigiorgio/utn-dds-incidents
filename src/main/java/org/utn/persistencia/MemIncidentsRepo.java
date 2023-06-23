package org.utn.persistencia;

import org.utn.dominio.incidencia.Incident;
import org.utn.dominio.incidencia.IncidentsRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class MemIncidentsRepo implements IncidentsRepo {

    public static List<Incident> incidents;

    public static MemIncidentsRepo uniqueInstance;

    public MemIncidentsRepo(List<Incident> incidents){
        MemIncidentsRepo.incidents = incidents;
    }

    public static MemIncidentsRepo getInstance(List<Incident> incidents) {
        if (uniqueInstance == null) {
            uniqueInstance = new MemIncidentsRepo(incidents);
        }
        return uniqueInstance;
    }

    public static MemIncidentsRepo getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new MemIncidentsRepo(new ArrayList<>());
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
        OptionalInt incidentToRemove =
                IntStream.range(0, incidents.size()).filter(i -> incidents.get(i).getId().equals(id)).findFirst();
        if (incidentToRemove.isPresent()) incidents.remove(incidentToRemove.getAsInt());
    }

    public Incident getIncidentById(Integer id) {
        List<Incident> result = incidents.stream().filter(i -> i.getId().equals(id)).collect(Collectors.toList());
        if (result.size() != 1) return null;
        return result.get(0);
    }

    public List<Incident> findIncidents(int quantity, String status, String orderBy, String place) {
        List<Incident> list = incidents;
        if (status != null) {
            list = list.stream().filter(i -> i.getStatusName().equals(status)).collect(Collectors.toList());
        }
        if (place != null) {
            list = list.stream().filter(i -> i.getCatalogCode().equals(place)).collect(Collectors.toList());;
        }
        if (orderBy != null) {
            if (orderBy.equals("createdAt")){
                list = getOrdered(list, true);
            }
            else list = getOrdered(list, false);
        }
        return filterByQuantity(list, quantity);
    }

    public int count() {
        return incidents.size();
    }

    public static List<Incident> filterByQuantity(List<Incident> list, int quantity) {
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
