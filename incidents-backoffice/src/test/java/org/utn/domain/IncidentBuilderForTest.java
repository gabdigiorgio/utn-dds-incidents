package org.utn.domain;

import org.utn.domain.incident.State;
import org.utn.domain.incident.Incident;

import java.time.LocalDate;

public class IncidentBuilderForTest {
    public static Incident DefaultIncident = new Incident("1234-56",
            LocalDate.of(2023, 5, 26),
            "Descripcion de prueba",
            "Operador de prueba",
            "Reportado por de prueba",
            null,
            "",
            State.DISMISSED);

    private String catalogCode = DefaultIncident.getCatalogCode();
    private LocalDate reportDate = DefaultIncident.getReportDate();
    private String description = DefaultIncident.getDescription();
    private String operator = DefaultIncident.getOperator();
    private String reportedBy = DefaultIncident.getReportedBy();
    private LocalDate closingDate = DefaultIncident.getClosingDate();
    private String rejectedReason = DefaultIncident.getRejectedReason();
    private State state = DefaultIncident.getState();

    public IncidentBuilderForTest withCatalogCode(String code) {
        catalogCode = code;
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

    public IncidentBuilderForTest withReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
        return this;
    }

    public IncidentBuilderForTest withClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
        return this;
    }

    public IncidentBuilderForTest withRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
        return this;
    }

    public IncidentBuilderForTest withState(State state) {
        this.state = state;
        return this;
    }

    public Incident build() {
        return new Incident(catalogCode, reportDate, description, operator, reportedBy, closingDate, rejectedReason, state);
    }
}
