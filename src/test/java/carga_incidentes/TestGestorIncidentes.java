package carga_incidentes;

import org.junit.Test;
import org.utn.aplicacion.GestorIncidencia;
import org.utn.persistencia.MemRepoIncidencias;
import org.utn.utils.exceptions.validador.FormatoCodigoCatalogInvalidoException;

import java.time.LocalDate;

public class TestGestorIncidentes {

    @Test
    public void testGestorIncidenciaReportado() throws Exception {
        GestorIncidencia gestor = new GestorIncidencia(MemRepoIncidencias.obtenerInstancia());

        String codigoCatalogo = "1234-56";
        LocalDate fechaReporte = LocalDate.of(2023, 5, 26);
        String descripcion = "Incidencia de prueba";
        String estado = "Asignado";
        String operador = "Operador";
        String personaReporto = "PersonaReporto";
        String motivoRechazo = "";

        gestor.crearIncidencia(codigoCatalogo,
                fechaReporte,
                descripcion,
                estado,
                operador,
                personaReporto,
                null,
                motivoRechazo);
    }
    @Test
    public void testGestorIncidenciaAsignado() throws Exception {
        GestorIncidencia gestor = new GestorIncidencia(MemRepoIncidencias.obtenerInstancia());

        String codigoCatalogo = "1234-56";
        LocalDate fechaReporte = LocalDate.of(2023, 5, 26);
        String descripcion = "Incidencia de prueba";
        String estado = "Asignado";
        String operador = "Operador";
        String personaReporto = "PersonaReporto";
        String motivoRechazo = "";

        gestor.crearIncidencia(codigoCatalogo,
                fechaReporte,
                descripcion,
                estado,
                operador,
                personaReporto,
                null,
                motivoRechazo);
    }

    @Test
    public void testGestorIncidenciaConfirmado() throws Exception {
        GestorIncidencia gestor = new GestorIncidencia(MemRepoIncidencias.obtenerInstancia());

        String codigoCatalogo = "1234-56";
        LocalDate fechaReporte = LocalDate.of(2023, 5, 26);
        String descripcion = "Incidencia de prueba";
        String estado = "Confirmado";
        String operador = "Operador";
        String personaReporto = "PersonaReporto";
        String motivoRechazo = "";

        gestor.crearIncidencia(codigoCatalogo,
                fechaReporte,
                descripcion,
                estado,
                operador,
                personaReporto,
                null,
                motivoRechazo);
    }

    @Test
    public void testGestorIncidenciaDesestimado() throws Exception {
        GestorIncidencia gestor = new GestorIncidencia(MemRepoIncidencias.obtenerInstancia());

        String codigoCatalogo = "1234-56";
        LocalDate fechaReporte = LocalDate.of(2023, 5, 26);
        String descripcion = "Incidencia de prueba";
        String estado = "Desestimado";
        String operador = "Operador";
        String personaReporto = "PersonaReporto";
        LocalDate fechaCierre = LocalDate.of(2023, 5, 27);
        String motivoRechazo = "";

        gestor.crearIncidencia(codigoCatalogo,
                fechaReporte,
                descripcion,
                estado,
                operador,
                personaReporto,
                fechaCierre,
                motivoRechazo);
    }

    @Test
    public void testGestorIncidenciaEnProgreso() throws Exception {
        GestorIncidencia gestor = new GestorIncidencia(MemRepoIncidencias.obtenerInstancia());

        String codigoCatalogo = "1234-56";
        LocalDate fechaReporte = LocalDate.of(2023, 5, 26);
        String descripcion = "Incidencia de prueba";
        String estado = "En progreso";
        String operador = "Operador";
        String personaReporto = "PersonaReporto";
        String motivoRechazo = "";

        gestor.crearIncidencia(codigoCatalogo,
                fechaReporte,
                descripcion,
                estado,
                operador,
                personaReporto,
                null,
                motivoRechazo);
    }

    @Test
    public void testGestorIncidenciaSolucionado() throws Exception {
        GestorIncidencia gestor = new GestorIncidencia(MemRepoIncidencias.obtenerInstancia());

        String codigoCatalogo = "1234-56";
        LocalDate fechaReporte = LocalDate.of(2023, 5, 26);
        String descripcion = "Incidencia de prueba";
        String estado = "Solucionado";
        String operador = "Operador";
        String personaReporto = "PersonaReporto";
        LocalDate fechaCierre = LocalDate.of(2023, 5, 27);
        String motivoRechazo = "";

        gestor.crearIncidencia(codigoCatalogo,
                fechaReporte,
                descripcion,
                estado,
                operador,
                personaReporto,
                fechaCierre,
                motivoRechazo);
    }

    @Test(expected = FormatoCodigoCatalogInvalidoException.class)
    public void testGestorIncidenciaCatalogoInvalido() throws Exception {
        GestorIncidencia gestor = new GestorIncidencia(MemRepoIncidencias.obtenerInstancia());

        String codigoCatalogo = "12347-56";
        LocalDate fechaReporte = LocalDate.of(2023, 5, 26);
        String descripcion = "Incidencia de prueba";
        String estado = "Asignado";
        String operador = "Operador";
        String personaReporto = "PersonaReporto";
        String motivoRechazo = "";

        gestor.crearIncidencia(codigoCatalogo,
                fechaReporte,
                descripcion,
                estado,
                operador,
                personaReporto,
                null,
                motivoRechazo);
    }

}
