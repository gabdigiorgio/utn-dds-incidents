package org.utn.dominio;

import org.utn.dominio.estado.Rejected;
import org.utn.dominio.estado.Status;
import org.utn.dominio.incidencia.CatalogCode;
import org.utn.dominio.incidencia.Incident;

import java.time.LocalDate;

public class IncidentBuilderForTest {
    public static Incident defaultIncident = new Incident(
            new CatalogCode("1234-56"),
            LocalDate.of(2023, 5, 26),
            "Descripcion de prueba",
            "Operador de prueba",
            "Reportado por de prueba",
            null,
            "",
            new Rejected()
    );

    private CatalogCode catalogCode = defaultIncident.getCatalogCode();
    private LocalDate reportDate = defaultIncident.getReportDate();
    private String description = defaultIncident.getDescription();
    private String operator = defaultIncident.getOperator();
    private String whoReported = defaultIncident.getWhoReported();
    private LocalDate closeDate = defaultIncident.getCloseDate();
    private String rejectionReason = defaultIncident.getRejectionReason();
    private Status status = defaultIncident.getStatus();

    public IncidentBuilderForTest withCatalogCode(String code) {
        catalogCode = new CatalogCode(code);
        return this;
    }

    public IncidentBuilderForTest withReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
        return this;
    }

    public IncidentBuilderForTest withDescription(String description) {
        this.description = description;
        return this;
    }

    public IncidentBuilderForTest withOperator(String operator) {
        this.operator = operator;
        return this;
    }

    public IncidentBuilderForTest withWhoReported(String whoReported) {
        this.whoReported = whoReported;
        return this;
    }

    public IncidentBuilderForTest withCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
        return this;
    }

    public IncidentBuilderForTest withRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
        return this;
    }

    public IncidentBuilderForTest withStatus(Status status) {
        this.status = status;
        return this;
    }

    public Incident build() {
        return
                new Incident(
                        catalogCode,
                        reportDate,
                        description,
                        operator,
                        whoReported,
                        closeDate,
                        rejectionReason,
                        status
                );
    }
}
