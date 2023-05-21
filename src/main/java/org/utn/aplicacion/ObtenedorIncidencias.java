package org.utn.aplicacion;

import org.utn.dominio.incidencia.Incidencia;
import org.utn.persistencia.RepoIncidencias;

import java.util.List;

public class ObtenedorIncidencias {
    private final RepoIncidencias repoIncidencias;

    public ObtenedorIncidencias(RepoIncidencias repoIncidencias) {
        this.repoIncidencias = repoIncidencias;
    }

    public List<Incidencia> obtenerIncidenciasByPlace(String code) {
        return repoIncidencias.obtenerIncidenciasByPlace(code);
    }
}
