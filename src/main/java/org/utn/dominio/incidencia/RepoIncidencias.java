package org.utn.dominio.incidencia;

import org.utn.dominio.estado.Estado;

import java.util.List;

public interface RepoIncidencias {
    void save(Incidencia incidencia);

    void update(Incidencia incidencia);

    void remove(Integer id);

    Incidencia getById(Integer id);

    List<Incidencia> findIncidents(int quantity, String status, String orderBy, String place);

    List<Incidencia> findByEstado(String estado, List<Incidencia> incidents);

    int count();

    List<Incidencia> ordenarPorMasReciente();

    List<Incidencia> ordenarPorLaMasVieja();

    List<Incidencia> obtenerIncidencias(int cantidad, String orden);

    List<Incidencia> obtenerIncidencias(int cantidad, Estado estado);

    List<Incidencia> findByPlace(String codigoCatalogo, List<Incidencia> incidents);

    List<Incidencia> obtenerIncidenciasByPlace(CodigoCatalogo codigoCatalogo);

    List<Incidencia> obtenerIncidenciasByEstado(int cantidad, String estado);
}
