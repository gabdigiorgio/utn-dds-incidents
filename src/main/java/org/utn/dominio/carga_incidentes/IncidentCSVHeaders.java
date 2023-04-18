package org.utn.dominio.carga_incidentes;

public enum IncidentCSVHeaders {
    CODE("Codigo de catalogo"),
    REPORT_DATE("Fecha de reporte"),
    DESCRIPTION("Descripcion"),
    STATUS("Estado"),
    OPERATOR("Operador"),
    REPORTER("Persona que lo reporto"),
    RESOLVED_DATE("Fecha cierre"),
    REJECTED_REASON("Motivo rechazo");

    private final String description;
    IncidentCSVHeaders(String description) {
        this.description = description;
    }

    public String get() {
        return description;
    }
}
