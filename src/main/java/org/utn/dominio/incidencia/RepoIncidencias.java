package org.utn.dominio.incidencia;

import org.utn.dominio.estado.Estado;

import java.util.List;

public interface RepoIncidencias {
    void save(Incidencia incidencia);

    void update(Incidencia incidencia);

    void remove(Integer id);

    Incidencia getById(Integer id);

    List<Incidencia> findByEstado(String estado);

    int count();

    List<Incidencia> ordenarPorMasReciente();

    List<Incidencia> ordenarPorLaMasVieja();

    List<Incidencia> incidenciasDeUnLugar(String lugar);

    List<Incidencia> obtenerIncidencias(int cantidad, String orden);

    List<Incidencia> obtenerIncidencias(int cantidad, Estado estado);

    List<Incidencia> obtenerIncidenciasByPlace(CodigoCatalogo codigoCatalogo);

    List<Incidencia> obtenerIncidenciasByEstado(int cantidad, String estado);
}
