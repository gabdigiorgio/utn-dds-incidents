package org.utn.domain.incident;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Fetch;
import org.utn.domain.incident.state.State;
import org.utn.domain.incident.state.StateConverter;
import org.utn.domain.incident.state.StateTransitionException;
import org.utn.domain.users.User;

import javax.naming.OperationNotSupportedException;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operator_id")
    public User operator;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    public User reportedBy;
    public LocalDate closingDate;
    public String rejectedReason;
    @Convert(converter = StateConverter.class)
    public State state;
    public String employee;

    public Incident(
        String catalogCode,
        LocalDate reportDate,
        String description,
        User operator,
        User reportedBy,
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

    public User getOperator() {
        return operator;
    }

    public User getReportedBy() {
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

    public void setState(State targetState) throws StateTransitionException {
        this.state.verifyCanTransition(targetState);
        this.state = targetState;
    }

    public void assignEmployee(String employee) throws StateTransitionException, IllegalArgumentException {
        this.setEmployee(employee);
        this.setState(State.ASSIGNED);
    }

    public void confirm() throws StateTransitionException {
        this.setState(State.CONFIRMED);
    }

    public void dismiss(String rejectedReason, LocalDate closingDate) throws IllegalArgumentException, StateTransitionException {
        this.setClosingDate(closingDate);
        this.setRejectedReason(rejectedReason);
        this.setState(State.DISMISSED);

    }

    public void startProgress() throws StateTransitionException {
        this.setState(State.IN_PROGRESS);
    }

    public void resolveIncident(LocalDate closingDate) throws StateTransitionException {
        this.setClosingDate(closingDate);
        this.setState(State.RESOLVED);
    }

    public String getEmployee() { return employee;}

    public void setEmployee(String employee) {
        if (employee == null || employee.isEmpty()) {
            throw new IllegalArgumentException("El campo 'empleado' no puede ser nulo o vacío.");
        }
        this.employee = employee;
    }

    public void setClosingDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("El campo 'fecha de cierre' no puede ser nulo.");
        }
        this.closingDate = date;
    }

    public void setDescription(String description) throws OperationNotSupportedException {
        if(this.state.equals(State.RESOLVED) || this.state.equals(State.DISMISSED)) {
            throw new OperationNotSupportedException("No es posible modificar la descripción en un estado final.");
        }
        this.description = description;
    }

    public void setReportedBy(User reportedBy) {
        this.reportedBy = reportedBy;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public void setRejectedReason(String rejectedReason) {
        if (rejectedReason == null || rejectedReason.isEmpty()) {
            throw new IllegalArgumentException("El campo 'motivo de rechazo' no puede ser nulo o vacío.");
        }
        this.rejectedReason = rejectedReason;
    }

    public void setOperator(User operator) { this.operator = operator; }
}