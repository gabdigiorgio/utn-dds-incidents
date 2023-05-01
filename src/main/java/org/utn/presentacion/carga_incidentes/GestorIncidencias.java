package org.utn.presentacion.carga_incidentes;

import org.utn.dominio.incidente.Incidencia;
import org.utn.dominio.incidente.factory.IncidenciaFactory;
import org.utn.infraestructura.persistencia.MemRepoIncidencias;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class GestorIncidencias {

    public GestorIncidencias() {}

    public String procesarLinea(String[] datos) throws Exception {

        MemRepoIncidencias repoIncidencias = MemRepoIncidencias.obtenerInstancia();

        String codigoDeCatalogo = datos[0];
        String fechaDeReporte = datos[1];
        String descripcion = datos[2];
        String estado = datos[3];
        String operador = datos[4];
        String reportador = datos[5];
        String fechaCierre = datos[6];
        String motivoRechazo = datos[7];

        // Validamos los datos de la línea
       Validador.validarEstado(estado, datos[7]);

        // Creamos la instancia de la incidencia correspondiente
        Map<String, Supplier<Incidencia>> factoryMethods = new HashMap<>();
        factoryMethods.put("Reportado", () -> IncidenciaFactory.crearIncidenciaReportado(codigoDeCatalogo, fechaDeReporte, descripcion, operador, reportador));
        factoryMethods.put("Asignado", () -> IncidenciaFactory.crearIncidenciaAsignado(codigoDeCatalogo, fechaDeReporte, descripcion, operador, reportador, fechaCierre, motivoRechazo));
        factoryMethods.put("Confirmado", () -> IncidenciaFactory.crearIncidenciaConfirmado(codigoDeCatalogo, fechaDeReporte, descripcion, operador, reportador, fechaCierre));
        factoryMethods.put("Desestimado", () -> IncidenciaFactory.crearIncidenciaDesestimado(codigoDeCatalogo, fechaDeReporte, descripcion, operador, reportador, fechaCierre, motivoRechazo));
        factoryMethods.put("En progreso", () -> IncidenciaFactory.crearIncidenciaEnProgreso(codigoDeCatalogo, fechaDeReporte, descripcion, operador, reportador, fechaCierre, motivoRechazo));
        factoryMethods.put("Solucionado", () -> IncidenciaFactory.crearIncidenciaSolucionado(codigoDeCatalogo, fechaDeReporte, descripcion, operador, reportador, fechaCierre, motivoRechazo));

        Supplier<Incidencia> factoryMethod = factoryMethods.get(estado);
        if (factoryMethod != null) {
            Incidencia nuevaIncidencia = factoryMethod.get();
            repoIncidencias.save(nuevaIncidencia);
        }

        return "";
    }



    // Otros métodos para operar sobre las incidencias
}

