package org.utn.dominio.incidencia;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.utn.dominio.estado.*;

import java.time.LocalDate;

public class Incident {
    private Integer id;
    public CatalogCode catalogCode;
    public LocalDate reportDate;
    public String description;
    public String operator;
    public String whoReported; // Posiblemente en un futuro sea una Clase (Pagina 7)
    public LocalDate closeDate;
    public String rejectionReason;
    public Status status;
    public String employee; // Posiblemente en un futuro sea una Clase (Pagina 8)

    public Incident(
        CatalogCode catalogCode,
        LocalDate reportDate,
        String description,
        String operator,
        String whoReported,
        LocalDate closeDate,
        String rejectionReason,
        Status status
    )
    {
        this.catalogCode = catalogCode;
        this.reportDate = reportDate;
        this.description = description;
        this.operator = operator;
        this.whoReported = whoReported;
        this.closeDate = closeDate;
        if (rejectionReason.isEmpty()) this.rejectionReason = "";
        else this.rejectionReason = rejectionReason;
        this.status = status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public CatalogCode getCatalogCode() {
        return catalogCode;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate getReportDate() {
        return reportDate;
    }

    public String getDescription() {
        return description;
    }

    public String getOperator() {
        return operator;
    }

    public String getWhoReported() {
        return whoReported;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate getCloseDate() {
        return closeDate;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public Status getStatus() {
        return status;
    }

    public String getStatusName(){return status.getStatusName();}

    public String getCreator(){
        return whoReported;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /******   Inicio metodos que impactan a estados   ******/
    public void assignEmployee(String employee) throws Exception {
        this.status.assignEmployee(this);
        this.setEmployee(employee);
    }

    public void confirmIncident() throws Exception {
        this.status.confirmIncident(this);
    }

    public void rejectIncident(String rejectionReason) throws Exception {
        this.status.rejectIncident(this);
        this.setRejectionReason(rejectionReason);
    }

    public void startProgress() throws Exception {
        this.status.startProgress(this);
    }

    public void solveIncident() throws Exception {
        this.status.resolveIncident(this);
    }


    /******   Fin metodos que impactan a estados   ******/
//////////////////////////////////////////
    public String getEmployee() { return employee;}

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public void setCloseDate(LocalDate date) {
        this.closeDate = date;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

}