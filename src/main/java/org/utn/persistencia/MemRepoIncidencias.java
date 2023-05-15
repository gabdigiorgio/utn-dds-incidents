package org.utn.persistencia;

import org.utn.dominio.estado.Estado;
import org.utn.dominio.incidencia.Incidencia;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class MemRepoIncidencias implements RepoIncidencias {
    private List<Incidencia> incidencias = new ArrayList<>();

    private static MemRepoIncidencias instanciaUnica;

    private MemRepoIncidencias() {
    }

    public static MemRepoIncidencias obtenerInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new MemRepoIncidencias();
        }
        return instanciaUnica;
    }

    public void save(Incidencia incidencia) {
        incidencias.add(incidencia);
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

    public int compararFechas(String fecha1, String fecha2) {
        LocalDate localDate1 = LocalDate.parse(fecha1, DateTimeFormatter.ofPattern("ddMMyyyy"));
        LocalDate localDate2 = LocalDate.parse(fecha2, DateTimeFormatter.ofPattern("ddMMyyyy"));
        return localDate1.compareTo(localDate2);
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
                lista = this.incidenciasDeUnLugar(orden);
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

    public List<Incidencia> obtenerIncidenciasByPlace(String code){
        List<Incidencia> lista = incidencias.stream().filter(i -> i.getCodigoCatalogo().equals(code)).collect(Collectors.toList());
        return lista;
    }
}
