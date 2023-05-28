package org.utn.dominio.incidencia.factory;

import org.utn.dominio.estado.*;
import org.utn.dominio.incidencia.CodigoCatalogo;
import org.utn.dominio.incidencia.Incidencia;

import java.time.LocalDate;

public class IncidenciaFactory {
    public static Incidencia crearIncidenciaReportado(CodigoCatalogo codigoCatalogo, LocalDate fechaReporte, String descripcion,
                                                      String operador, String personaReporto) {
        Estado estado = new Reportado();
        return new Incidencia(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, null, "", estado);
    }

    public static Incidencia crearIncidenciaAsignado(CodigoCatalogo codigoCatalogo, LocalDate fechaReporte, String descripcion,
                                                     String operador, String personaReporto, LocalDate fechaCierre, String motivoRechazo) {
        Estado estado = new Asignado();
        return new Incidencia(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, motivoRechazo, estado);
    }

    public static Incidencia crearIncidenciaConfirmado(CodigoCatalogo codigoCatalogo, LocalDate fechaReporte, String descripcion,
                                                       String operador, String personaReporto, LocalDate fechaCierre) {
        Estado estado = new Confirmado();
        return new Incidencia(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, "", estado);
    }

    public static Incidencia crearIncidenciaDesestimado(CodigoCatalogo codigoCatalogo, LocalDate fechaReporte, String descripcion,
                                                        String operador, String personaReporto, LocalDate fechaCierre, String motivoRechazo) {
        Estado estado = new Desestimado();
        return new Incidencia(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, motivoRechazo, estado);
    }

    public static Incidencia crearIncidenciaEnProgreso(CodigoCatalogo codigoCatalogo, LocalDate fechaReporte, String descripcion,
                                                       String operador, String personaReporto, LocalDate fechaCierre, String motivoRechazo) {
        Estado estado = new EnProgreso();
        return new Incidencia(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, motivoRechazo, estado);
    }

    public static Incidencia crearIncidenciaSolucionado(CodigoCatalogo codigoCatalogo, LocalDate fechaReporte, String descripcion,
                                                        String operador, String personaReporto, LocalDate fechaCierre, String motivoRechazo) {
        Estado estado = new Solucionado();
        return new Incidencia(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, motivoRechazo, estado);
    }
}