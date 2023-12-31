package org.utn.domain.incident.factory;

import org.utn.domain.incident.Incident;
import org.utn.domain.incident.state.State;
import org.utn.domain.users.User;

import java.time.LocalDate;

public class IncidentFactory {
    public static Incident createReportedIncident(String catalogCode, LocalDate reportDate, String description,
                                                  User operator, User reportedBy) {
        State state = State.REPORTED;
        return new Incident(catalogCode, reportDate, description, operator, reportedBy, null, "", state);
    }

    public static Incident createAssignedIncident(String catalogCode, LocalDate reportDate, String description,
                                                  User operator, User reportedBy, LocalDate closingDate, String rejectedReason) {
        State state = State.ASSIGNED;
        return new Incident(catalogCode, reportDate, description, operator, reportedBy, closingDate, rejectedReason, state);
    }

    public static Incident createConfirmedIncident(String catalogCode, LocalDate reportDate, String description,
                                                   User operator, User reportedBy, LocalDate closingDate) {
        State state = State.CONFIRMED;
        return new Incident(catalogCode, reportDate, description, operator, reportedBy, closingDate, "", state);
    }

    public static Incident createDismissedIncident(String catalogCode, LocalDate reportDate, String description,
                                                   User operator, User reportedBy, LocalDate closingDate, String rejectedReason) {
        State state = State.DISMISSED;
        return new Incident(catalogCode, reportDate, description, operator, reportedBy, closingDate, rejectedReason, state);
    }

    public static Incident createInProgressIncident(String catalogCode, LocalDate reportDate, String description,
                                                    User operator, User reportedBy, LocalDate closingDate, String rejectedReason) {
        State state = State.IN_PROGRESS;
        return new Incident(catalogCode, reportDate, description, operator, reportedBy, closingDate, rejectedReason, state);
    }

    public static Incident createResolvedIncident(String catalogCode, LocalDate reportDate, String description,
                                                  User operator, User reportedBy, LocalDate closingDate, String rejectedReason) {
        State state = State.RESOLVED;
        return new Incident(catalogCode, reportDate, description, operator, reportedBy, closingDate, rejectedReason, state);
    }
}