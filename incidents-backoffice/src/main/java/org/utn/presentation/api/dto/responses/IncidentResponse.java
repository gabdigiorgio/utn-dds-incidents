package org.utn.presentation.api.dto.responses;

import org.utn.domain.incident.Incident;

public class IncidentResponse {
    private Integer id;
    private String catalogCode;
    private String reportDate;
    private String description;
    private String reporterEmail;
    private String closingDate;
    private String rejectedReason;
    private String state;
    private String employee;
    private String operator;

    public IncidentResponse(Incident incident) {
        this.id = incident.getId();
        this.catalogCode = incident.getCatalogCode();
        this.reportDate = incident.getReportDate().toString();
        this.description = incident.getDescription();
        this.reporterEmail = incident.getReportedBy().getEmail();
        if (incident.getClosingDate() != null) this.closingDate = incident.getClosingDate().toString();
        this.rejectedReason = incident.getRejectedReason();
        this.state = incident.getState().toString();
        this.employee = incident.getEmployee();
        this.operator = incident.getOperator();
    }

    public Integer getId() {
        return id;
    }

    public String getCatalogCode() {
        return catalogCode;
    }

    public String getReportDate() {
        return reportDate;
    }

    public String getDescription() {
        return description;
    }

    public String getReporterEmail() {
        return reporterEmail;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public String getState() {
        return state;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReporterEmail(String reporterEmail) {
        this.reporterEmail = reporterEmail;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }
}
