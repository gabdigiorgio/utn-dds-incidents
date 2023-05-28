package org.utn.presentacion;


import org.junit.Test;
import org.utn.presentacion.carga_incidentes.Validador;
import org.utn.utils.exceptions.validador.DatosIncompletosException;

public class TestValidarDatosIncompletos {

    @Test(expected = DatosIncompletosException.class)
    public void testValidarDatosIncompletos() throws Exception {
        String codigoCatalogo = "";
        String fechaReporte = "";
        String descripcion = "";
        String estado = "Invalido";
        String operador = "";
        String personaReporto = "";
        String fechaCierre = "";
        String motivoRechazo = "";
        Validador.validar(codigoCatalogo, fechaReporte, descripcion, estado, operador, personaReporto, fechaCierre, motivoRechazo);

    }
}