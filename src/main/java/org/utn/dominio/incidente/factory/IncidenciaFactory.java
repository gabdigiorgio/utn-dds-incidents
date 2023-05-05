package org.utn.dominio.incidente.factory;

import org.utn.dominio.estado.*;
import org.utn.dominio.incidente.Incidencia;

public class IncidenciaFactory {
    public static Incidencia crearIncidenciaReportado(String codigoCatalogo, String fechaReporte, String descripcion,
                                               String operador, String personaReporto){
        Estado estado = new Reportado();
        return new Incidencia(codigoCatalogo,fechaReporte,descripcion,operador,personaReporto,"","",estado);
    }
    public static Incidencia crearIncidenciaAsignado(String codigoCatalogo, String fechaReporte, String descripcion,
                                                     String operador, String personaReporto, String fechaCierre, String motivoRechazo){
        Estado estado = new Asignado();
        return new Incidencia(codigoCatalogo,fechaReporte,descripcion,operador,personaReporto,fechaCierre,motivoRechazo,estado);
    }
    public static Incidencia crearIncidenciaConfirmado(String codigoCatalogo, String fechaReporte, String descripcion,
                                                     String operador, String personaReporto, String fechaCierre){
        Estado estado = new Confirmado();
        return new Incidencia(codigoCatalogo,fechaReporte,descripcion,operador,personaReporto,fechaCierre,"",estado);
    }
    public static Incidencia crearIncidenciaDesestimado(String codigoCatalogo, String fechaReporte, String descripcion,
                                                     String operador, String personaReporto, String fechaCierre, String motivoRechazo){
        Estado estado = new Desestimado();
        return new Incidencia(codigoCatalogo,fechaReporte,descripcion,operador,personaReporto,fechaCierre,motivoRechazo,estado);
    }
    public static Incidencia crearIncidenciaEnProgreso(String codigoCatalogo, String fechaReporte, String descripcion,
                                                     String operador, String personaReporto, String fechaCierre, String motivoRechazo){
        Estado estado = new EnProgreso();
        return new Incidencia(codigoCatalogo,fechaReporte,descripcion,operador,personaReporto,fechaCierre,motivoRechazo,estado);
    }
    public static Incidencia crearIncidenciaSolucionado(String codigoCatalogo, String fechaReporte, String descripcion,
                                                     String operador, String personaReporto, String fechaCierre, String motivoRechazo){
        Estado estado = new Solucionado();
        return new Incidencia(codigoCatalogo,fechaReporte,descripcion,operador,personaReporto,fechaCierre,motivoRechazo,estado);
    }
}