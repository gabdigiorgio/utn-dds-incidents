package org.utn.presentacion;


import org.junit.Test;
import org.utn.presentacion.carga_incidentes.Validador;
import org.utn.utils.exceptions.validador.DatosIncompletosException;
import org.utn.utils.exceptions.validador.FormatoFechaInvalidaException;

public class TestValidarDatos {
    private String codigoCatalogo = "1234-56";
    private String fechaReporte = "28052023";
    private String descripcion = "Descripcion de prueba";
    private String estado = "Asignado";
    private String operador = "";
    private String personaReporto = "";
    private String fechaCierre = "";
    private String motivoRechazo = "";

    @Test
    public void noDebeLanzarDatosIncompletosExcepcion() throws Exception {

        whenValidate();
    }
    @Test(expected = DatosIncompletosException.class)
    public void debeLanzarDatosIncompletosExcepcionPorCodigoCatalogo() throws Exception {

        givenCodigoCatalogo("");

        whenValidate();
    }

    @Test(expected = DatosIncompletosException.class)
    public void debeLanzarDatosIncompletosExcepcionPorDescripcion() throws Exception {

        givenDescripcion("");

        whenValidate();
    }

    @Test(expected = DatosIncompletosException.class)
    public void debeLanzarDatosIncompletosExcepcionPorFechaReporte() throws Exception {

        givenFechaReporte("");

        whenValidate();
    }

    @Test(expected = DatosIncompletosException.class)
    public void debeLanzarDatosIncompletosExcepcionPorEstado() throws Exception {

        givenEstado("");

        whenValidate();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExceptionPorEstadoReportadoConMotivoRechazo() throws Exception {

        givenEstado("Reportado");
        givenMotivoRechazo("Motivo de rechazo");

        whenValidate();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExceptionPorEstadoDesestimadoSinMotivoRechazo() throws Exception {

        givenEstado("Desestimado");

        whenValidate();
    }

    @Test(expected = FormatoFechaInvalidaException.class)
    public void debeLanzarFormatoFechaInvalidaExceptionPorFechaReporte() throws Exception {

        givenFechaReporte("123");

        whenValidate();
    }

    @Test(expected = FormatoFechaInvalidaException.class)
    public void debeLanzarFormatoFechaInvalidaExceptionPorFechaCierre() throws Exception {

        givenFechaCierre("123");

        whenValidate();
    }

    private void givenCodigoCatalogo(String codigoCatalogo) {
        this.codigoCatalogo = codigoCatalogo;
    }

    private void givenDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    private void givenFechaReporte(String fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    private void givenFechaCierre(String fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    private void givenEstado(String estado) {
        this.estado = estado;
    }

    private void  givenMotivoRechazo(String motivoRechazo){
        this.motivoRechazo = motivoRechazo;
    }

    private void whenValidate() throws Exception {
        Validador.validar(codigoCatalogo, fechaReporte, descripcion, estado, operador, personaReporto, fechaCierre, motivoRechazo);
    }
}