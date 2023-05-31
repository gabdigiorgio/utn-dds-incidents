package org.utn.aplicacion;

import org.utn.dominio.incidencia.CodigoCatalogo;
import org.utn.dominio.incidencia.Incidencia;
import org.utn.dominio.incidencia.RepoIncidencias;
import org.utn.utils.exceptions.validador.DatosIncompletosException;
import org.utn.utils.exceptions.validador.FormatoCodigoCatalogoInvalidoException;

import java.util.List;

public class ObtenedorIncidencias {
    private final RepoIncidencias repoIncidencias;

    public ObtenedorIncidencias(RepoIncidencias repoIncidencias) {
        this.repoIncidencias = repoIncidencias;
    }

    public Incidencia getIncidentById(Integer id) {
        return repoIncidencias.getById(id);
    }

    public List<Incidencia> obtenerIncidenciasByPlace(String codigoCatalogo) throws DatosIncompletosException, FormatoCodigoCatalogoInvalidoException {
        return repoIncidencias.obtenerIncidenciasByPlace(new CodigoCatalogo(codigoCatalogo));
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
