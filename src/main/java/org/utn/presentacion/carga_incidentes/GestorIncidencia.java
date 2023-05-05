package org.utn.presentacion.carga_incidentes;

import org.utn.dominio.incidente.Incidencia;
import org.utn.dominio.incidente.factory.IncidenciaFactory;
import org.utn.infraestructura.persistencia.MemRepoIncidencias;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GestorIncidencia {

    public GestorIncidencia() {}

    public void procesarLinea(String codigoCatalogo, String fechaReporte, String descripcion, String estado, String operador, String personaReporto, String fechaCierre, String motivoRechazo) throws Exception {

        MemRepoIncidencias repoIncidencias = MemRepoIncidencias.obtenerInstancia();

        // Validamos los datos de la línea
       Validador.validar(codigoCatalogo,fechaReporte,descripcion,estado,operador,personaReporto,fechaCierre,motivoRechazo);

        // Creamos la instancia de la incidencia correspondiente
        Map<String, Supplier<Incidencia>> factoryMethods = new HashMap<>();
        factoryMethods.put("Reportado", () -> IncidenciaFactory.crearIncidenciaReportado(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto));
        factoryMethods.put("Asignado", () -> IncidenciaFactory.crearIncidenciaAsignado(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, motivoRechazo));
        factoryMethods.put("Confirmado", () -> IncidenciaFactory.crearIncidenciaConfirmado(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre));
        factoryMethods.put("Desestimado", () -> IncidenciaFactory.crearIncidenciaDesestimado(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, motivoRechazo));
        factoryMethods.put("En progreso", () -> IncidenciaFactory.crearIncidenciaEnProgreso(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, motivoRechazo));
        factoryMethods.put("Solucionado", () -> IncidenciaFactory.crearIncidenciaSolucionado(codigoCatalogo, fechaReporte, descripcion, operador, personaReporto, fechaCierre, motivoRechazo));

        Supplier<Incidencia> factoryMethod = factoryMethods.get(estado);
        if (factoryMethod != null) {
            Incidencia nuevaIncidencia = factoryMethod.get();
            repoIncidencias.save(nuevaIncidencia);
        }
    }
}

