package org.utn.dominio.incidencia;

import java.util.List;

public interface RepoIncidencias {
    void save(Incidencia incidencia);

    void update(Incidencia incidencia);

    void remove(Integer id);

    Incidencia getById(Integer id);

    List<Incidencia> findIncidents(int quantity, String status, String orderBy, String place);

    int count();
}
