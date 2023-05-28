package org.utn.aplicacion;

import org.jetbrains.annotations.NotNull;
import org.utn.dominio.incidencia.CodigoCatalogo;
import org.utn.dominio.incidencia.Incidencia;
import org.utn.dominio.incidencia.RepoIncidencias;
import org.utn.dominio.incidencia.factory.IncidenciaFactory;
import org.utn.utils.exceptions.validador.FormatoCodigoCatalogoInvalidoException;

import java.time.LocalDate;

public class GestorIncidencia {

    private final RepoIncidencias repoIncidencias;

    public GestorIncidencia(RepoIncidencias repoIncidencias) {
        this.repoIncidencias = repoIncidencias;
    }

    public void crearIncidencia(String codigoCatalogo,
                                LocalDate fechaReporte,
                                String descripcion,
                                String estado,
                                String operador,
                                String personaReporto,
                                LocalDate fechaCierre,
                                String motivoRechazo) throws FormatoCodigoCatalogoInvalidoException {
        Incidencia nuevaIncidencia = nuevaIncidencia(new CodigoCatalogo(codigoCatalogo),
                fechaReporte,
                descripcion,
                estado,
                operador,
                personaReporto,
                fechaCierre,
                motivoRechazo);
        repoIncidencias.save(nuevaIncidencia);
    }

    @NotNull
    private static Incidencia nuevaIncidencia(CodigoCatalogo codigoCatalogo,
                                              LocalDate fechaReporte,
                                              String descripcion,
                                              String estado,
                                              String operador,
                                              String personaReporto,
                                              LocalDate fechaCierre,
                                              String motivoRechazo) {
        Incidencia nuevaIncidencia;
        switch (estado) {
            case "Reportado":
                nuevaIncidencia = IncidenciaFactory.crearIncidenciaReportado(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto);
                break;
            case "Asignado":
                nuevaIncidencia = IncidenciaFactory.crearIncidenciaAsignado(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, motivoRechazo);
                break;
            case "Confirmado":
                nuevaIncidencia = IncidenciaFactory.crearIncidenciaConfirmado(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre);
                break;
            case "Desestimado":
                nuevaIncidencia = IncidenciaFactory.crearIncidenciaDesestimado(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, motivoRechazo);
                break;
            case "En progreso":
                nuevaIncidencia = IncidenciaFactory.crearIncidenciaEnProgreso(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, motivoRechazo);
                break;
            case "Solucionado":
                nuevaIncidencia = IncidenciaFactory.crearIncidenciaSolucionado(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, motivoRechazo);
                break;
            default:
                throw new IllegalArgumentException("Estado de incidencia inválido: " + estado);
        }
        return nuevaIncidencia;
    }
}

