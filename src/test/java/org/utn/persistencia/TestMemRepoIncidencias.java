package org.utn.persistencia;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.utn.dominio.IncidenciasBuilderForTest;
import org.utn.dominio.incidencia.EnumEstado;
import org.utn.dominio.incidencia.Estado;
import org.utn.dominio.incidencia.Incidencia;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestMemRepoIncidencias {

    private static Incidencia incidencia;

    private static List<Incidencia> listaIncidencias = new ArrayList<>();

    private static final MemRepoIncidencias repoIncidencias= MemRepoIncidencias.obtenerInstancia(listaIncidencias);

    @After
    public void limpiarListaIncidencias() {
        listaIncidencias.clear();
    }

    @Before
    public void cargarIncidencias() {
        givenIncidenciaWithEstado(EnumEstado.REPORTADO);
        givenIncidenciaWithEstado(EnumEstado.ASIGNADO);
        givenIncidenciaWithEstado(EnumEstado.CONFIRMADO);
        givenIncidenciaWithEstado(EnumEstado.DESESTIMADO);
        givenIncidenciaWithEstado(EnumEstado.EN_PROGRESO);
        givenIncidenciaWithEstado(EnumEstado.SOLUCIONADO);
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

        List<Incidencia> incidenciasFiltradas = whenFindByEstado(EnumEstado.REPORTADO);

        debeFiltrarseIncidencias(1, incidenciasFiltradas);

        debeSerIncidenciaDeEstado(EnumEstado.REPORTADO.getClass(), incidenciasFiltradas);
    }

    @Test
    public void debePoderFiltrarUnaIncidenciaPorEstadoAsignado(){

        List<Incidencia> incidenciasFiltradas = whenFindByEstado(EnumEstado.ASIGNADO);

        debeFiltrarseIncidencias(1, incidenciasFiltradas);

        debeSerIncidenciaDeEstado(EnumEstado.ASIGNADO.getClass(), incidenciasFiltradas);
    }

    @Test
    public void debePoderFiltrarUnaIncidenciaPorEstadoConfirmado(){

        List<Incidencia> incidenciasFiltradas = whenFindByEstado(EnumEstado.CONFIRMADO);

        debeFiltrarseIncidencias(1, incidenciasFiltradas);

        debeSerIncidenciaDeEstado(EnumEstado.CONFIRMADO.getClass(), incidenciasFiltradas);
    }

    @Test
    public void debePoderFiltrarUnaIncidenciaPorEstadoDesestimado(){

        List<Incidencia> incidenciasFiltradas = whenFindByEstado(EnumEstado.DESESTIMADO);

        debeFiltrarseIncidencias(1, incidenciasFiltradas);

        debeSerIncidenciaDeEstado(EnumEstado.DESESTIMADO.getClass(), incidenciasFiltradas);
    }

    @Test
    public void debePoderFiltrarUnaIncidenciaPorEstadoEnProgreso(){

        List<Incidencia> incidenciasFiltradas = whenFindByEstado(EnumEstado.EN_PROGRESO);

        debeFiltrarseIncidencias(1, incidenciasFiltradas);

        debeSerIncidenciaDeEstado(EnumEstado.EN_PROGRESO.getClass(), incidenciasFiltradas);
    }

    @Test
    public void debePoderFiltrarUnaIncidenciaPorEstadoSolucionado(){

        List<Incidencia> incidenciasFiltradas = whenFindByEstado(EnumEstado.SOLUCIONADO);

        debeFiltrarseIncidencias(1, incidenciasFiltradas);

        debeSerIncidenciaDeEstado(EnumEstado.SOLUCIONADO.getClass(), incidenciasFiltradas);
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
        return repoIncidencias.findIncidents(10, estado.getNombreEstado(), null, null);
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
        assertEquals(repoIncidencias.obtenerIncidencias(4,EnumEstado.ASIGNADO).size(),3);

        assertEquals(repoIncidencias.obtenerIncidencias(3,EnumEstado.REPORTADO).size(),1);
    }*/

    /*@Test
    public void ordenXLugar(){

        assertEquals(repoIncidencias.obtenerIncidencias(2,"1533-24").size(),2);
    }*/

}