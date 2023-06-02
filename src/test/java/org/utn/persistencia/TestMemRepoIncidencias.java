package org.utn.persistencia;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.utn.dominio.IncidenciasBuilderForTest;
import org.utn.dominio.estado.*;
import org.utn.dominio.incidencia.Incidencia;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestMemRepoIncidencias {

    private static Incidencia incidencia;

    private static List<Incidencia> listaIncidencias = new ArrayList<>();

    private static final MemRepoIncidencias repoIncidencias= MemRepoIncidencias.obtenerInstancia(listaIncidencias);
    private final String empleado="Jorge";

    @After
    public void limpiarListaIncidencias() {
        listaIncidencias.clear();
    }

    @Before
    public void cargarIncidencias() {
        givenIncidenciaWithEstado(new Reportado());
        givenIncidenciaWithEstado(new Asignado());
        givenIncidenciaWithEstado(new Confirmado());
        givenIncidenciaWithEstado(new Desestimado());
        givenIncidenciaWithEstado(new EnProgreso());
        givenIncidenciaWithEstado(new Solucionado());
    }

    @Test
    public void debePoderGuardarUnaIncidencia(){

        limpiarListaIncidencias();

        givenIncidencia(new IncidenciasBuilderForTest().build());

        whenSaveIncidencia();

        debeGuardarIncidencia();

    }

    @Test
    public void debePoderFiltrarUnaIncidenciasPorEstadoReportado(){

        List<Incidencia> incidenciasFiltradas = whenFindByEstado(new Reportado());

        debeFiltrarseIncidencias(1, incidenciasFiltradas);

        debeSerIncidenciaDeEstado(Reportado.class, incidenciasFiltradas);
    }

    @Test
    public void debePoderFiltrarUnaIncidenciaPorEstadoAsignado(){

        List<Incidencia> incidenciasFiltradas = whenFindByEstado(new Asignado());

        debeFiltrarseIncidencias(1, incidenciasFiltradas);

        debeSerIncidenciaDeEstado(Asignado.class, incidenciasFiltradas);
    }

    @Test
    public void debePoderFiltrarUnaIncidenciaPorEstadoConfirmado(){

        List<Incidencia> incidenciasFiltradas = whenFindByEstado(new Confirmado());

        debeFiltrarseIncidencias(1, incidenciasFiltradas);

        debeSerIncidenciaDeEstado(Confirmado.class, incidenciasFiltradas);
    }

    @Test
    public void debePoderFiltrarUnaIncidenciaPorEstadoDesestimado(){

        List<Incidencia> incidenciasFiltradas = whenFindByEstado(new Desestimado());

        debeFiltrarseIncidencias(1, incidenciasFiltradas);

        debeSerIncidenciaDeEstado(Desestimado.class, incidenciasFiltradas);
    }

    @Test
    public void debePoderFiltrarUnaIncidenciaPorEstadoEnProgreso(){

        List<Incidencia> incidenciasFiltradas = whenFindByEstado(new EnProgreso());

        debeFiltrarseIncidencias(1, incidenciasFiltradas);

        debeSerIncidenciaDeEstado(EnProgreso.class, incidenciasFiltradas);
    }

    @Test
    public void debePoderFiltrarUnaIncidenciaPorEstadoSolucionado(){

        List<Incidencia> incidenciasFiltradas = whenFindByEstado(new Solucionado());

        debeFiltrarseIncidencias(1, incidenciasFiltradas);

        debeSerIncidenciaDeEstado(Solucionado.class, incidenciasFiltradas);
    }

    private void givenIncidencia(Incidencia incidencia){
        this.incidencia = incidencia;
    }

    private void givenIncidenciaWithEstado(Estado estado) {
        incidencia = new IncidenciasBuilderForTest().withEstado(estado).build();
        listaIncidencias.add(incidencia);
    }

    private void whenSaveIncidencia(){
        repoIncidencias.save(incidencia);
    }

    private List<Incidencia> whenFindByEstado(Estado estado) {
        return repoIncidencias.findByEstado(estado.getNombreEstado(), repoIncidencias.incidencias);
    }

    private void debeGuardarIncidencia() {
        assertEquals(1 ,listaIncidencias.size());
    }

    private void debeFiltrarseIncidencias(int cantidad, List<Incidencia> incidenciasFiltradas){
        assertEquals(cantidad, incidenciasFiltradas.size());
    }

    private void debeSerIncidenciaDeEstado(Class <? extends  Estado> type, List <Incidencia> incidenciasFiltradas){
        assertTrue(type.isInstance(incidenciasFiltradas.get(0).getEstado()));
    }

    /*
    @Test
    public void ultimosReportados(){

        assertEquals(repoIncidencias.obtenerIncidencias(3,"ordenarPorMasReciente").size(),3);
    }

    @Test
    public void desdeLaMasVieja(){

        assertEquals(repoIncidencias.obtenerIncidencias(5,"ordenarPorLaMasVieja").size(),4);
    }

    @Test
    public void ordenXestado(){
        //pido 4 incidencias pero solo hay 3 de ese estado
        assertEquals(repoIncidencias.obtenerIncidencias(4,new Asignado()).size(),3);

        assertEquals(repoIncidencias.obtenerIncidencias(3,new Reportado()).size(),1);
    }*/

    /*@Test
    public void ordenXLugar(){

        assertEquals(repoIncidencias.obtenerIncidencias(2,"1533-24").size(),2);
    }*/

}