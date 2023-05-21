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

    public List <Incidencia> obtenerIncidenciasByEstado(int cantidad, String estado) {
        return repoIncidencias.obtenerIncidenciasByEstado(cantidad, estado);
    }

    public List<Incidencia> obtenerIncidenciasOrdenadasPorLasMasViejas(int cantidad) {
        return repoIncidencias.obtenerIncidencias(cantidad,"ordenarPorLaMasVieja");
    }

    public List<Incidencia> obtenerIncidenciasOrdenadasPorLasMasRecientes(int cantidad) {
        return repoIncidencias.obtenerIncidencias(cantidad,"ordenarPorMasReciente");
    }
}
