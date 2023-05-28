package org.utn.presentacion;


import org.junit.Test;
import org.utn.presentacion.carga_incidentes.Validador;
import org.utn.utils.exceptions.validador.DatosIncompletosException;

public class TestValidarDatosIncompletos {
    private String codigoCatalogo = "1234-56";
    private String fechaReporte = "28052023";
    private String descripcion = "Descripcion de prueba";
    private String estado = "Asignado";
    private String operador = "Operador";
    private String personaReporto = "Persona que reporto";
    private String fechaCierre = "29052023";
    private String motivoRechazo = "Motivo de rechazo";

    @Test
    public void noDebeLanzarDatosIncompletosExcepcion() throws Exception {
        
        whenValidate();
    }
    @Test(expected = DatosIncompletosException.class)
    public void debeLanzarDatosIncompletosExcepcionPorCodigoCatalogo() throws Exception {

        codigoCatalogo = "";

        whenValidate();
    }

    @Test(expected = DatosIncompletosException.class)
    public void debeLanzarDatosIncompletosExcepcionPorDescripcion() throws Exception {

        descripcion = "";

        whenValidate();
    }

    @Test(expected = DatosIncompletosException.class)
    public void debeLanzarDatosIncompletosExcepcionPorFechaReporte() throws Exception {

        fechaReporte = "";

        whenValidate();
    }

    @Test(expected = DatosIncompletosException.class)
    public void debeLanzarDatosIncompletosExcepcionPorEstado() throws Exception {

        estado = "";

        whenValidate();
    }

    private void whenValidate() throws Exception {
        Validador.validar(codigoCatalogo, fechaReporte, descripcion, estado, operador, personaReporto, fechaCierre, motivoRechazo);
    }
}