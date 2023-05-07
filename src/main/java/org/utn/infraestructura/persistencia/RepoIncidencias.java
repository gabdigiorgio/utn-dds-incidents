package org.utn.infraestructura.persistencia;

import org.utn.dominio.estado.Estado;
import org.utn.dominio.incidente.Incidencia;

import java.util.List;

public interface RepoIncidencias {
    void save(Incidencia incidencia);
    List<Incidencia> findByEstado(String estado);
    int count();
    List<Incidencia> ultimasReportadas(int n);
    List<Incidencia> ordenarPorLaMasVieja(int n);
    List<Incidencia> ordenarPorLugar(int n,String lugar);
    List<Incidencia> obtenerIncidencias(int cantidad,String orden);
    List<Incidencia> obtenerIncidencias(int cantidad, Estado estado);
}
