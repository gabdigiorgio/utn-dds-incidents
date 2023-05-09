package org.utn.infraestructura.persistencia;

import org.utn.dominio.estado.Estado;
import org.utn.dominio.incidente.Incidencia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class MemRepoIncidencias implements RepoIncidencias {
    private List<Incidencia> incidencias = new ArrayList<>();

    private static MemRepoIncidencias instanciaUnica;
    private MemRepoIncidencias() {}

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
    public List<Incidencia> ordenarPorMasReciente(){
       Collections.sort(incidencias, (unaIncidencia, otra) -> unaIncidencia.getFechaReporte().compareToIgnoreCase(otra.getFechaReporte()));
        Collections.reverse(incidencias);
        return incidencias;
    }

    public List<Incidencia> ordenarPorLaMasVieja(){
        Collections.sort(incidencias, (unaIncidencia, otra) -> unaIncidencia.getFechaReporte().compareToIgnoreCase(otra.getFechaReporte()));

        return incidencias;
    }

    public List<Incidencia> incidenciasDeUnLugar(String lugar){
        return incidencias.stream().filter(i -> i.getCodigoCatalogo().equals(lugar)).collect(Collectors.toList());
    }

    public List<Incidencia> obtenerIncidencias(int cantidad,String orden){
        List<Incidencia> lista;
        if(orden.equalsIgnoreCase("ordenarPorMasReciente")) lista=this.ordenarPorMasReciente();
        else if(orden.equalsIgnoreCase("ordenarPorLaMasVieja")) lista=this.ordenarPorLaMasVieja();
        else lista=this.incidenciasDeUnLugar(orden);
        //si orden no es un estado y no es ninguna de las anteriores, es un lugar especifico.
        if (lista.size()<=cantidad) return lista;
        return lista.subList(0,cantidad);
    }
    public List<Incidencia> obtenerIncidencias(int cantidad,Estado estado){
        List<Incidencia> lista;
        lista=this.findByEstado(estado.getNombreEstado());
        if (lista.size()<=cantidad) return lista;
        return lista.subList(0,cantidad);
    }
}
