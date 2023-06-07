package org.utn.persistencia;

import org.utn.dominio.incidencia.Incidencia;
import org.utn.dominio.incidencia.RepoIncidencias;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class MemRepoIncidencias implements RepoIncidencias {
    public static List<Incidencia> incidencias;

    public static MemRepoIncidencias instanciaUnica;

    public MemRepoIncidencias(List<Incidencia> incidencias){
        MemRepoIncidencias.incidencias = incidencias;
    }

    public static MemRepoIncidencias obtenerInstancia(List<Incidencia> incidencias) {
        if (instanciaUnica == null) {
            instanciaUnica = new MemRepoIncidencias(incidencias);
        }
        return instanciaUnica;
    }

    public static MemRepoIncidencias obtenerInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new MemRepoIncidencias(new ArrayList<>());
        }
        return instanciaUnica;
    }

    public void save(Incidencia incidencia) {
        Integer lastId = incidencias.stream().mapToInt(i -> i.getId()).max().orElse(0);
        incidencia.setId(lastId + 1);
        incidencias.add(incidencia);
    }

    public void update(Incidencia incidencia) {
        OptionalInt incidentToRemove = IntStream.range(0, incidencias.size()).filter(i -> incidencias.get(i).getId().equals(incidencia.getId())).findFirst();
        if (incidentToRemove.isPresent()) incidencias.remove(incidentToRemove.getAsInt());
        this.save(incidencia);
    }

    public void remove(Integer id) {
        OptionalInt incidentToRemove = IntStream.range(0, incidencias.size()).filter(i -> incidencias.get(i).getId().equals(id)).findFirst();
        if (incidentToRemove.isPresent()) incidencias.remove(incidentToRemove.getAsInt());
    }

    public Incidencia getById(Integer id) {
        List<Incidencia> result = incidencias.stream().filter(i -> i.getId().equals(id)).collect(Collectors.toList());
        if (result.size() != 1) return null;
        return result.get(0);
    }

    public List<Incidencia> findIncidents(int quantity, String status, String orderBy, String place) {
        List<Incidencia> list = incidencias;
        if (status != null) {
            list = list.stream().filter(i -> i.getNombreEstado().equals(status)).collect(Collectors.toList());
        }
        if (place != null) {
            list = list.stream().filter(i -> i.getCodigoCatalogo().equals(place)).collect(Collectors.toList());;
        }
        if (orderBy != null) {
            if (orderBy == "createdAt") list = getOrdered(list, true);
            else list = getOrdered(list, false);
        }
        return filtrarPorCantidad(list, quantity);
    }

    public int count() {
        return incidencias.size();
    }

    public static List<Incidencia> filtrarPorCantidad(List<Incidencia> lista, int cantidad) {
        if (lista.size() <= cantidad) {
            return lista;
        } else {
            return lista.subList(0, cantidad);
        }
    }

    public static List<Incidencia> getOrdered(List<Incidencia> incidents, Boolean newsFirst) {
        Collections.sort(incidents, (unaIncidencia, otra) -> unaIncidencia.getFechaReporte().compareTo(otra.getFechaReporte()));
        if (newsFirst) Collections.reverse(incidents);
        return incidents;
    }
}
