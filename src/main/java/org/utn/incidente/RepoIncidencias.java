package org.utn.incidente;

import java.util.List;

public interface RepoIncidencias {
    void save(Incidencia incidencia);
    List<Incidencia> findByEstado(String estado);
    int count();
}
