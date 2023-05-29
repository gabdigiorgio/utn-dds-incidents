package org.utn.dominio;

import org.utn.dominio.estado.Desestimado;
import org.utn.dominio.estado.Estado;
import org.utn.dominio.incidencia.CodigoCatalogo;
import org.utn.dominio.incidencia.Incidencia;

import java.time.LocalDate;

public class IncidenciasBuilderForTest {
    public static Incidencia IncendeciaDefault = new Incidencia(new CodigoCatalogo("1234-56"),
            LocalDate.of(2023, 5, 26),
            "Descripcion de prueba",
            "Operador de prueba",
            "Reportado por de prueba",
            null,
            "",
            new Desestimado());

    private CodigoCatalogo codigoCatalogo = IncendeciaDefault.getCodigoCatalogo();
    private LocalDate fechaReporte = IncendeciaDefault.getFechaReporte();
    private String descripcion = IncendeciaDefault.getDescripcion();
    private String operador = IncendeciaDefault.getOperador();
    private String reportadoPor = IncendeciaDefault.getReportadoPor();
    private LocalDate fechaCierre = IncendeciaDefault.getFechaCierre();
    private String motivoRechazo = IncendeciaDefault.getMotivoRechazo();
    private Estado estado = IncendeciaDefault.getEstado();

    public IncidenciasBuilderForTest withCodigoCatalogo(String codigo) {
        codigoCatalogo = new CodigoCatalogo(codigo);
        return this;
    }

    public IncidenciasBuilderForTest withFechaReporte(LocalDate fechaReporte) {
        this.fechaReporte = fechaReporte;
        return this;
    }

    public IncidenciasBuilderForTest withDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public IncidenciasBuilderForTest withOperador(String operador) {
        this.operador = operador;
        return this;
    }

    public IncidenciasBuilderForTest withReportadoPor(String reportadoPor) {
        this.reportadoPor = reportadoPor;
        return this;
    }

    public IncidenciasBuilderForTest withFechaCierre(LocalDate fechaCierre) {
        this.fechaCierre = fechaCierre;
        return this;
    }

    public IncidenciasBuilderForTest withMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
        return this;
    }

    public IncidenciasBuilderForTest withEstado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public Incidencia build() {
        return new Incidencia(codigoCatalogo, fechaReporte, descripcion, operador, reportadoPor, fechaCierre, motivoRechazo, estado);
    }
}
