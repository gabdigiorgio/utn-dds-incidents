package org.utn.domain.incident;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    public String catalogCode;
    public LocalDate reportDate;
    public String description;
    public String operator;
    public String reportedBy;
    public LocalDate closingDate;
    public String rejectedReason;
    @Convert(converter = StateConverter.class)
    public State state;
    public String employee;

    public Incident(
        String catalogCode,
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

    public String getCatalogCode() { return catalogCode; }

    public void setCatalogCode(String catalogCode) { this.catalogCode = catalogCode; }

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

    public String getReporter(){
        return reportedBy;
    }

    public void setState(State targetState) throws StateTransitionException {
        this.state.verifyCanTransition(targetState);
        this.state = targetState;
    }

    public void assignEmployee(String employee) throws StateTransitionException, IllegalArgumentException {
        this.setState(State.ASSIGNED);
        if (employee == null || employee.isEmpty()) throw new IllegalArgumentException("The 'employee' field cannot be null or empty.");
        this.setEmployee(employee);
    }

    public void confirmIncident() throws StateTransitionException {
        this.setState(State.CONFIRMED);
    }

    public void dismissIncident(String rejectedReason) throws IllegalArgumentException, StateTransitionException {
        this.setState(State.DISMISSED);
        if (rejectedReason == null || rejectedReason.isEmpty()) throw new IllegalArgumentException("The 'rejection reason' field cannot be null or empty.");
        this.setRejectedReason(rejectedReason);
    }

    public void startProgress() throws StateTransitionException {
        this.setState(State.IN_PROGRESS);
    }

    public void resolveIncident() throws StateTransitionException {
        this.setState(State.RESOLVED);
    }

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

    public void updateState(State nextState, String employee, String rejectedReason) throws StateTransitionException {
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