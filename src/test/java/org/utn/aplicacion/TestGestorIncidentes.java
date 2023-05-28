package org.utn.aplicacion;

import org.junit.Assert;
import org.junit.Test;
import org.utn.dominio.estado.*;
import org.utn.dominio.incidencia.Incidencia;

import java.time.LocalDate;

public class TestGestorIncidentes {

    private FakeRepoIncidencias repo = new FakeRepoIncidencias();
    private GestorIncidencia gestor = new GestorIncidencia(repo);
    private Incidencia incidenciaEsperada;

    @Test
    public void debeGuardarIncidenciaEnRepo() {

        givenIncidenciaEsperada(new IncidenciasBuilderForTest().build());

        whenCrearIncidencia();

        Incidencia incidenciaActual = repo.getIncidenciaGuardada();

        Assert.assertNotNull(incidenciaActual);
    }

    @Test
    public void debeCrearInicdenciaCorrectaReportado() {

        givenIncidenciaEsperada(new IncidenciasBuilderForTest().withEstado(new Reportado()).build());

        whenCrearIncidencia();

        debeCrearIncidenciaCorrectamente();
    }

    @Test
    public void debeCrearInicdenciaCorrectaAsignado() {

        givenIncidenciaEsperada(new IncidenciasBuilderForTest().withEstado(new Asignado()).build());

        whenCrearIncidencia();

        debeCrearIncidenciaCorrectamente();
    }

    @Test
    public void debeCrearInicdenciaCorrectaConfirmado() {

        givenIncidenciaEsperada(new IncidenciasBuilderForTest().withEstado(new Confirmado()).build());

        whenCrearIncidencia();

        debeCrearIncidenciaCorrectamente();
    }

    @Test
    public void debeCrearInicdenciaCorrectaDesestimado() {

        givenIncidenciaEsperada(new IncidenciasBuilderForTest()
                .withEstado(new Desestimado())
                .withFechaCierre(LocalDate.of(2023, 5, 28))
                .withMotivoRechazo("Motivo rechazo de prueba")
                .build());

        whenCrearIncidencia();

        debeCrearIncidenciaCorrectamente();
    }

    @Test
    public void debeCrearInicdenciaCorrectaEnProgreso() {

        givenIncidenciaEsperada(new IncidenciasBuilderForTest().withEstado(new EnProgreso()).build());

        whenCrearIncidencia();

        debeCrearIncidenciaCorrectamente();
    }

    @Test
    public void debeCrearInicdenciaCorrectaSolucionado() {

        givenIncidenciaEsperada(new IncidenciasBuilderForTest()
                .withEstado(new Solucionado())
                .withFechaCierre(LocalDate.of(2023, 5, 28))
                .build());

        whenCrearIncidencia();

        debeCrearIncidenciaCorrectamente();
    }

    private void debeCrearIncidenciaCorrectamente() {
        Incidencia incidenciaActual = repo.getIncidenciaGuardada();
        Assert.assertEquals(incidenciaEsperada.getCodigoCatalogo(), incidenciaActual.getCodigoCatalogo());
        Assert.assertEquals(incidenciaEsperada.getFechaReporte(), incidenciaActual.getFechaReporte());
        Assert.assertEquals(incidenciaEsperada.getDescripcion(), incidenciaActual.getDescripcion());
        Assert.assertEquals(incidenciaEsperada.getOperador(), incidenciaActual.getOperador());
        Assert.assertEquals(incidenciaEsperada.getReportadoPor(), incidenciaActual.getReportadoPor());
        Assert.assertEquals(incidenciaEsperada.getFechaCierre(), incidenciaActual.getFechaCierre());
        Assert.assertEquals(incidenciaEsperada.getMotivoRechazo(), incidenciaActual.getMotivoRechazo());
        Assert.assertEquals(incidenciaEsperada.getNombreEstado(), incidenciaActual.getNombreEstado());
    }

    private void givenIncidenciaEsperada(Incidencia incidenciaEsperada) {
        this.incidenciaEsperada = incidenciaEsperada;
    }

    private void whenCrearIncidencia() {
        gestor.crearIncidencia(incidenciaEsperada.getCodigoCatalogo().getCodigo(),
                incidenciaEsperada.getFechaReporte(),
                incidenciaEsperada.getDescripcion(),
                incidenciaEsperada.getEstado().getNombreEstado(),
                incidenciaEsperada.getOperador(),
                incidenciaEsperada.getReportadoPor(),
                incidenciaEsperada.getFechaCierre(),
                incidenciaEsperada.getMotivoRechazo());
    }

}
