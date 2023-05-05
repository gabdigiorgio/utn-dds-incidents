package org.utn.infraestructura.persistencia;

import org.utn.dominio.incidente.Incidencia;

import java.util.List;

public interface RepoIncidencias {
    void save(Incidencia incidencia);
    List<Incidencia> findByEstado(String estado);
    int count();
}
