package org.utn.dominio.incidencia.factory;

import org.utn.dominio.incidencia.CodigoCatalogo;
import org.utn.dominio.incidencia.EnumEstado;
import org.utn.dominio.incidencia.Estado;
import org.utn.dominio.incidencia.Incidencia;

import java.time.LocalDate;

public class IncidenciaFactory {
    public static Incidencia crearIncidenciaReportado(CodigoCatalogo codigoCatalogo, LocalDate fechaReporte, String descripcion,
                                                      String operador, String personaReporto) {
        Estado estado = EnumEstado.REPORTADO;
        return new Incidencia(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, null, "", estado);
    }

    public static Incidencia crearIncidenciaAsignado(CodigoCatalogo codigoCatalogo, LocalDate fechaReporte, String descripcion,
                                                     String operador, String personaReporto, LocalDate fechaCierre, String motivoRechazo) {
        Estado estado = EnumEstado.ASIGNADO;
        return new Incidencia(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, motivoRechazo, estado);
    }

    public static Incidencia crearIncidenciaConfirmado(CodigoCatalogo codigoCatalogo, LocalDate fechaReporte, String descripcion,
                                                       String operador, String personaReporto, LocalDate fechaCierre) {
        Estado estado = EnumEstado.CONFIRMADO;
        return new Incidencia(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, "", estado);
    }

    public static Incidencia crearIncidenciaDesestimado(CodigoCatalogo codigoCatalogo, LocalDate fechaReporte, String descripcion,
                                                        String operador, String personaReporto, LocalDate fechaCierre, String motivoRechazo) {
        Estado estado = EnumEstado.DESESTIMADO;
        return new Incidencia(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, motivoRechazo, estado);
    }

    public static Incidencia crearIncidenciaEnProgreso(CodigoCatalogo codigoCatalogo, LocalDate fechaReporte, String descripcion,
                                                       String operador, String personaReporto, LocalDate fechaCierre, String motivoRechazo) {
        Estado estado = EnumEstado.EN_PROGRESO;
        return new Incidencia(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, motivoRechazo, estado);
    }

    public static Incidencia crearIncidenciaSolucionado(CodigoCatalogo codigoCatalogo, LocalDate fechaReporte, String descripcion,
                                                        String operador, String personaReporto, LocalDate fechaCierre, String motivoRechazo) {
        Estado estado = EnumEstado.SOLUCIONADO;
        return new Incidencia(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, motivoRechazo, estado);
    }
}