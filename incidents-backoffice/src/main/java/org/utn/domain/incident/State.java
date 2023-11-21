package org.utn.domain.incident;

import com.fasterxml.jackson.annotation.JsonValue;
import org.utn.utils.ExceptionConstants;

public interface State {
    @JsonValue
    String getStateName();
    default void assignEmployee(Incident incident) throws StateTransitionException {
        String msgException = String.format(ExceptionConstants.ERROR_ASSIGN_EMPLOYEE_TRANSITION, incident.getState().getStateName());
        throw new StateTransitionException(msgException);
    }
    default void confirmIncident(Incident incident) throws StateTransitionException {
        String msgException = String.format(ExceptionConstants.ERROR_CONFIRM_INCIDENT_TRANSITION, incident.getState().getStateName());
        throw new StateTransitionException(msgException);
    }
    default void dismissIncident(Incident incident) throws StateTransitionException {
        String msgException = String.format(ExceptionConstants.ERROR_DISMISS_INCIDENT_TRANSITION, incident.getState().getStateName());
        throw new StateTransitionException(msgException);
    }
    default void startProgress(Incident incident) throws StateTransitionException {
        String msgException = String.format(ExceptionConstants.ERROR_START_PROGRESS_TRANSITION, incident.getState().getStateName());
        throw new StateTransitionException(msgException);
    }
    default void resolveIncident(Incident incident) throws StateTransitionException{
        String msgException = String.format(ExceptionConstants.ERROR_RESOLVE_INCIDENT_TRANSITION, incident.getState().getStateName());
        throw new StateTransitionException(msgException);
    }
}