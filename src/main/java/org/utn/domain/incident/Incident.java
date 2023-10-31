package org.utn.domain.incident;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //#TODO
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public CatalogCode catalogCode;
    public LocalDate reportDate;
    public String description;
    public String operator;
    public String reportedBy; // Posiblemente en un futuro sea una Clase (Pagina 7)
    public LocalDate closingDate;
    public String rejectedReason;
    @Convert(converter = StateConverter.class)
    public State state;
    public String employee; // Posiblemente en un futuro sea una Clase (Pagina 8)

    public Incident(
        CatalogCode catalogCode,
        LocalDate reportDate,
        String description,
        String operator,
        String reportedBy,
        LocalDate closingDate,
        String rejectedReason,
        State state
    ) {
        this.catalogCode = catalogCode;
        this.reportDate = reportDate;
        this.description = description;
        this.operator = operator;
        this.reportedBy = reportedBy;
        this.closingDate = closingDate;
        if (rejectedReason.isEmpty()) this.rejectedReason = "";
        else this.rejectedReason = rejectedReason;
        this.state = state;
    }

    protected Incident() {
        super();
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

    public String getReportedBy() {
        return reportedBy;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate getClosingDate() {
        return closingDate;
    }

    public String getRejectedReason() {
        return rejectedReason;
    }

    public State getState() {
        return state;
    }

    public String getStateName(){return state.getStateName();}

    public String getReporter(){
        return reportedBy;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    /******   Inicio metodos que impactan a estados   ******/
    public void assignEmployee(String employee) throws StateTransitionException, IllegalArgumentException {
        if (employee == null || employee.isEmpty()) throw new IllegalArgumentException("El campo 'empleado' no puede ser nulo ni vacío.");
        this.state.assignEmployee(this);
        this.setEmployee(employee);
    }

    public void confirmIncident() throws StateTransitionException {
        this.state.confirmIncident(this);
    }

    public void dismissIncident(String motivoRechazo) throws IllegalArgumentException, StateTransitionException {
        if (motivoRechazo == null || motivoRechazo.isEmpty()) throw new IllegalArgumentException("El campo 'motivo de rechazo' no puede ser nulo ni vacío.");
        this.state.dismissIncident(this);
        this.setRejectedReason(motivoRechazo);
    }

    public void startProgress() throws StateTransitionException {
        this.state.startProgress(this);
    }

    public void resolveIncident() throws StateTransitionException {
        this.state.resolveIncident(this);
    }

    /******   Fin metodos que impactan a estados   ******/
//////////////////////////////////////////
    public String getEmployee() { return employee;}

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public void setClosingDate(LocalDate date) {
        this.closingDate = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public void setRejectedReason(String rejectedReason) {
        this.rejectedReason = rejectedReason;
    }

    public void updateState(StateEnum nextState, String employee, String rejectedReason) throws StateTransitionException {
        switch (nextState) {
            case ASSIGNED:
                this.assignEmployee(employee);
                break;
            case CONFIRMED:
                this.confirmIncident();
                break;
            case DISMISSED:
                this.dismissIncident(rejectedReason);
                break;
            case IN_PROGRESS:
                this.startProgress();
                break;
            case RESOLVED:
                this.resolveIncident();
                break;
            default:
                throw new StateTransitionException("Estado deseado inválido");
        }
    }
}