package org.utn.persistencia;

import org.utn.dominio.estado.Estado;
import org.utn.dominio.incidencia.CodigoCatalogo;
import org.utn.dominio.incidencia.Incidencia;
import org.utn.dominio.incidencia.RepoIncidencias;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class MemRepoIncidencias implements RepoIncidencias {
    private List<Incidencia> incidencias = new ArrayList<>();

    private static MemRepoIncidencias instanciaUnica;

    private MemRepoIncidencias() {
    }

    private MemRepoIncidencias(List<Incidencia> incidencias){
        this.incidencias = incidencias;
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

    public List<Incidencia> findByEstado(String estado) {
        return incidencias.stream().filter(i -> i.getNombreEstado().equals(estado)).collect(Collectors.toList());
    }

    public int count() {
        return incidencias.size();
    }

    //n= cantidad de incidencias que desea ver
    //ordenado desde la mas reciente
    public List<Incidencia> ordenarPorMasReciente() {
        Collections.sort(incidencias, (unaIncidencia, otra) -> compararFechas(unaIncidencia.getFechaReporte(), otra.getFechaReporte()));
        Collections.reverse(incidencias);
        return incidencias;
    }

    public List<Incidencia> ordenarPorLaMasVieja() {
        Collections.sort(incidencias, (unaIncidencia, otra) -> compararFechas(unaIncidencia.getFechaReporte(), otra.getFechaReporte()));
        return incidencias;
    }

    public int compararFechas(LocalDate unaFecha, LocalDate otraFecha) {
        return unaFecha.compareTo(otraFecha);
    }

    public List<Incidencia> incidenciasDeUnLugar(String lugar) {
        return incidencias.stream().filter(i -> i.getCodigoCatalogo().equals(lugar)).collect(Collectors.toList());
    }

    public List<Incidencia> obtenerIncidencias(int cantidad, String orden) {
        List<Incidencia> lista;
        switch (orden) {
            case "ordenarPorMasReciente":
                lista = this.ordenarPorMasReciente();
                break;
            case "ordenarPorLaMasVieja":
                lista = this.ordenarPorLaMasVieja();
                break;
            default:
                lista = this.incidenciasDeUnLugar(orden); // TODO estan mezclados casos de usos, obtencion por orden != obtencion por lugar
        }
        return filtrarPorCantidad(lista, cantidad);
    }

    public List<Incidencia> obtenerIncidencias(int cantidad, Estado estado) {
        List<Incidencia> lista;
        lista = this.findByEstado(estado.getNombreEstado());
        return filtrarPorCantidad(lista, cantidad);
    }

    public List<Incidencia> filtrarPorCantidad(List<Incidencia> lista, int cantidad) {
        if (lista.size() <= cantidad) {
            return lista;
        } else {
            return lista.subList(0, cantidad);
        }
    }
    public List<Incidencia> obtenerIncidenciasByEstado(int cantidad,String estado){
        List<Incidencia> lista = incidencias.stream().filter(i -> i.getNombreEstado().equals(estado)).collect(Collectors.toList());

        if (lista.size()<=cantidad) return lista;
        return lista.subList(0,cantidad);
    }

    public List<Incidencia> obtenerIncidenciasByPlace(CodigoCatalogo code){
        List<Incidencia> lista = incidencias.stream().filter(i -> i.getCodigoCatalogo().equals(code)).collect(Collectors.toList());
        return lista;
    }
}
