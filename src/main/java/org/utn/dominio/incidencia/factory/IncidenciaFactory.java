package org.utn.dominio.incidencia.factory;

import org.utn.dominio.estado.*;
import org.utn.dominio.incidencia.CodigoCatalogo;
import org.utn.dominio.incidencia.Incidencia;

public class IncidenciaFactory {
    public static Incidencia crearIncidenciaReportado(CodigoCatalogo codigoCatalogo, String fechaReporte, String descripcion,
                                                      String operador, String personaReporto){
        Estado estado = new Reportado();
        return new Incidencia(codigoCatalogo,fechaReporte,descripcion,operador,personaReporto,"","",estado);
    }
    public static Incidencia crearIncidenciaAsignado(CodigoCatalogo codigoCatalogo, String fechaReporte, String descripcion,
                                                     String operador, String personaReporto, String fechaCierre, String motivoRechazo){
        Estado estado = new Asignado();
        return new Incidencia(codigoCatalogo,fechaReporte,descripcion,operador,personaReporto,fechaCierre,motivoRechazo,estado);
    }
    public static Incidencia crearIncidenciaConfirmado(CodigoCatalogo codigoCatalogo, String fechaReporte, String descripcion,
                                                     String operador, String personaReporto, String fechaCierre){
        Estado estado = new Confirmado();
        return new Incidencia(codigoCatalogo,fechaReporte,descripcion,operador,personaReporto,fechaCierre,"",estado);
    }
    public static Incidencia crearIncidenciaDesestimado(CodigoCatalogo codigoCatalogo, String fechaReporte, String descripcion,
                                                     String operador, String personaReporto, String fechaCierre, String motivoRechazo){
        Estado estado = new Desestimado();
        return new Incidencia(codigoCatalogo,fechaReporte,descripcion,operador,personaReporto,fechaCierre,motivoRechazo,estado);
    }
    public static Incidencia crearIncidenciaEnProgreso(CodigoCatalogo codigoCatalogo, String fechaReporte, String descripcion,
                                                     String operador, String personaReporto, String fechaCierre, String motivoRechazo){
        Estado estado = new EnProgreso();
        return new Incidencia(codigoCatalogo,fechaReporte,descripcion,operador,personaReporto,fechaCierre,motivoRechazo,estado);
    }
    public static Incidencia crearIncidenciaSolucionado(CodigoCatalogo codigoCatalogo, String fechaReporte, String descripcion,
                                                     String operador, String personaReporto, String fechaCierre, String motivoRechazo){
        Estado estado = new Solucionado();
        return new Incidencia(codigoCatalogo,fechaReporte,descripcion,operador,personaReporto,fechaCierre,motivoRechazo,estado);
    }
}