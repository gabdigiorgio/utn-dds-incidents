package org.utn.dominio;

import org.junit.Test;
import org.utn.dominio.estado.*;
import org.utn.dominio.incidencia.CodigoCatalogo;
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

        givenIncidenciaWithEstado(new Reportado());

        whenAsignarEmpleado();

        debePasarA(Asignado.class);
        debeAsignarseEmpleado();
    }

    @Test
    public void debePoderPasarDeAsignadoAConfirmado() throws Exception {

        givenIncidenciaWithEstado(new Asignado());

        whenConfirmarIncidencia();

        debePasarA(Confirmado.class);
    }

    @Test
    public void debePoderPasarDeAsignadoADesestimado() throws Exception {

        givenIncidenciaWithEstado(new Asignado());

        whenDesestimarIncidencia();

        debePasarA(Desestimado.class);
    }

    // Tests de transicion de estado: Reportado

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlConfirmarIncidenciaReportada() throws Exception{

        givenIncidenciaWithEstado(new Reportado());

        whenConfirmarIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlIniciarProgresoIncidenciaReportada() throws Exception{

        givenIncidenciaWithEstado(new Reportado());

        whenIniciarProgresoIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlResolverIncidenciaReportada() throws Exception{

        givenIncidenciaWithEstado(new Reportado());

        whenResolverIncidencia();
    }

    // Tests de transicion de estado: Asignado

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlAsignarEmpleadoIncidenciaAsignada() throws Exception{

        givenIncidenciaWithEstado(new Asignado());

        whenAsignarEmpleado();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlIniciarProgresoIncidenciaAsignada() throws Exception{

        givenIncidenciaWithEstado(new Asignado()); // TODO: revisar si puede pasar de asignado -> en progreso

        whenIniciarProgresoIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlResolverIncidenciaAsignada() throws Exception{

        givenIncidenciaWithEstado(new Asignado());

        whenResolverIncidencia();
    }

    // Tests de transicion de estado: Confirmado

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlAsignarEmpleadoIncidenciaConfirmada() throws Exception{

        givenIncidenciaWithEstado(new Confirmado());

        whenAsignarEmpleado();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlConfirmarIncidenciaConfirmada() throws Exception{

        givenIncidenciaWithEstado(new Confirmado());

        whenConfirmarIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlResolverIncidenciaConfirmada() throws Exception{

        givenIncidenciaWithEstado(new Confirmado());

        whenResolverIncidencia();
    }

    // Tests de transicion de estado: Desestimado

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlAsignarEmpleadoIncidenciaDesestimada() throws Exception{

        givenIncidenciaWithEstado(new Desestimado());

        whenAsignarEmpleado();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlConfirmarIncidenciaDesestimada() throws Exception{

        givenIncidenciaWithEstado(new Desestimado());

        whenConfirmarIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlDesestimarIncidenciaDesestimada() throws Exception{

        givenIncidenciaWithEstado(new Desestimado());

        whenDesestimarIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlIniciarProgresoIncidenciaDesestimada() throws Exception{

        givenIncidenciaWithEstado(new Desestimado());

        whenIniciarProgresoIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlResolverIncidenciaDesestimada() throws Exception{

        givenIncidenciaWithEstado(new Desestimado());

        whenResolverIncidencia();
    }

    // Tests de transicion de estado: En Progreso

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlAsignarEmpleadoIncidenciaEnProgreso() throws Exception{

        givenIncidenciaWithEstado(new EnProgreso());

        whenAsignarEmpleado();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlConfirmarIncidenciaEnProgreso() throws Exception{

        givenIncidenciaWithEstado(new EnProgreso());

        whenConfirmarIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlDesestimarIncidenciaEnProgreso() throws Exception{

        givenIncidenciaWithEstado(new EnProgreso());

        whenDesestimarIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlIniciarProgresoIncidenciaEnProgreso() throws Exception{

        givenIncidenciaWithEstado(new EnProgreso());

        whenIniciarProgresoIncidencia();
    }

    // Tests de transicion de estado: Solucionado

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlAsignarEmpleadoIncidenciaSolucionada() throws Exception{

        givenIncidenciaWithEstado(new Solucionado());

        whenAsignarEmpleado();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlConfirmarIncidenciaSolucionada() throws Exception{

        givenIncidenciaWithEstado(new Solucionado());

        whenConfirmarIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlDesestimarIncidenciaSolucionada() throws Exception{

        givenIncidenciaWithEstado(new Solucionado());

        whenDesestimarIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlIniciarProgresoIncidenciaSolucionada() throws Exception{

        givenIncidenciaWithEstado(new Solucionado());

        whenIniciarProgresoIncidencia();
    }

    @Test(expected = Exception.class)
    public void debeLanzarExcepcionAlResolverIncidenciaSolucionada() throws Exception{

        givenIncidenciaWithEstado(new Solucionado());

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
