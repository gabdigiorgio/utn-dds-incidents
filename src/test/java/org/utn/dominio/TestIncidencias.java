package org.utn.dominio;

import org.junit.Test;
import org.utn.dominio.incidencia.CodigoCatalogo;
import org.utn.dominio.incidencia.EnumEstado;
import org.utn.dominio.incidencia.Estado;
import org.utn.dominio.incidencia.Incidencia;
import org.utn.utils.exceptions.validador.FormatoCodigoCatalogoInvalidoException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestIncidencias {
    private Incidencia incidencia;
    private final String empleado = "Empleado de prueba";

    @Test(expected = FormatoCodigoCatalogoInvalidoException.class)
    public void debeLanzarFormatoCodigoCatalogoInvalidoExceptionPorCodigoCatalogo() throws FormatoCodigoCatalogoInvalidoException {

        new CodigoCatalogo("123456");
    }

    // Tests de transiciones de estados

    @Test
    public void debePoderPasarDeReportadoAAsignado() throws Exception {

        givenIncidenciaWithEstado(EnumEstado.REPORTADO);

        whenAsignarEmpleado();

        debePasarA(EnumEstado.ASIGNADO.getClass());
        debeAsignarseEmpleado();
    }

    @Test
    public void debePoderPasarDeAsignadoAConfirmado() throws Exception {

        givenIncidenciaWithEstado(EnumEstado.ASIGNADO);

        whenConfirmarIncidencia();

        debePasarA(EnumEstado.CONFIRMADO.getClass());
    }

    @Test
    public void debePoderPasarDeAsignadoADesestimado() throws Exception {

        givenIncidenciaWithEstado(EnumEstado.ASIGNADO);

        whenDesestimarIncidencia();

        debePasarA(EnumEstado.DESESTIMADO.getClass());
    }

    // Tests de transicion de estado: Reportado

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlConfirmarIncidenciaReportada() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.REPORTADO);

        whenConfirmarIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlIniciarProgresoIncidenciaReportada() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.REPORTADO);

        whenIniciarProgresoIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlResolverIncidenciaReportada() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.REPORTADO);

        whenResolverIncidencia();
    }

    // Tests de transicion de estado: Asignado

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlAsignarEmpleadoIncidenciaAsignada() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.ASIGNADO);

        whenAsignarEmpleado();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlIniciarProgresoIncidenciaAsignada() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.ASIGNADO); // TODO: revisar si puede pasar de asignado -> en progreso

        whenIniciarProgresoIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlResolverIncidenciaAsignada() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.ASIGNADO);

        whenResolverIncidencia();
    }

    // Tests de transicion de estado: Confirmado

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlAsignarEmpleadoIncidenciaConfirmada() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.CONFIRMADO);

        whenAsignarEmpleado();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlConfirmarIncidenciaConfirmada() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.CONFIRMADO);

        whenConfirmarIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlResolverIncidenciaConfirmada() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.CONFIRMADO);

        whenResolverIncidencia();
    }

    // Tests de transicion de estado: Desestimado

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlAsignarEmpleadoIncidenciaDesestimada() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.DESESTIMADO);

        whenAsignarEmpleado();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlConfirmarIncidenciaDesestimada() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.DESESTIMADO);

        whenConfirmarIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlDesestimarIncidenciaDesestimada() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.DESESTIMADO);

        whenDesestimarIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlIniciarProgresoIncidenciaDesestimada() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.DESESTIMADO);

        whenIniciarProgresoIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlResolverIncidenciaDesestimada() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.DESESTIMADO);

        whenResolverIncidencia();
    }

    // Tests de transicion de estado: En Progreso

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlAsignarEmpleadoIncidenciaEnProgreso() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.EN_PROGRESO);

        whenAsignarEmpleado();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlConfirmarIncidenciaEnProgreso() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.EN_PROGRESO);

        whenConfirmarIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlDesestimarIncidenciaEnProgreso() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.EN_PROGRESO);

        whenDesestimarIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlIniciarProgresoIncidenciaEnProgreso() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.EN_PROGRESO);

        whenIniciarProgresoIncidencia();
    }

    // Tests de transicion de estado: Solucionado

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlAsignarEmpleadoIncidenciaSolucionada() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.SOLUCIONADO);

        whenAsignarEmpleado();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlConfirmarIncidenciaSolucionada() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.SOLUCIONADO);

        whenConfirmarIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlDesestimarIncidenciaSolucionada() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.SOLUCIONADO);

        whenDesestimarIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlIniciarProgresoIncidenciaSolucionada() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.SOLUCIONADO);

        whenIniciarProgresoIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlResolverIncidenciaSolucionada() throws Exception{

        givenIncidenciaWithEstado(EnumEstado.SOLUCIONADO);

        whenResolverIncidencia();
    }

    private void givenIncidenciaWithEstado(Estado estado) {
        incidencia = new IncidenciasBuilderForTest().withEstado(estado).build();
    }

    private void whenAsignarEmpleado() throws Exception {
        incidencia.asignarEmpleado(empleado);
    }

    private void whenConfirmarIncidencia() throws Exception {
        incidencia.confirmarIncidencia();
    }

    private void whenDesestimarIncidencia() throws Exception {
        incidencia.desestimarIncidencia("");
    }

    private void whenResolverIncidencia() throws Exception {
        incidencia.resolverIncidencia();
    }

    private void whenIniciarProgresoIncidencia() throws Exception {
        incidencia.iniciarProgreso();
    }

    private void debeAsignarseEmpleado() {
        assertEquals(incidencia.getEmpleado(), empleado);
    }

    private void debePasarA(Class<? extends Estado> type) {
        assertTrue(type.isInstance(incidencia.getEstado()));
    }

}
