package org.utn.persistencia;

import org.utn.dominio.estado.Estado;
import org.utn.dominio.incidencia.Incidencia;

import java.util.List;

public interface RepoIncidencias {
    void save(Incidencia incidencia);
    List<Incidencia> findByEstado(String estado);
    int count();
    List<Incidencia> ordenarPorMasReciente();
    List<Incidencia> ordenarPorLaMasVieja();
    List<Incidencia> incidenciasDeUnLugar(String lugar);
    List<Incidencia> obtenerIncidencias(int cantidad,String orden);
    List<Incidencia> obtenerIncidencias(int cantidad, Estado estado);
}
