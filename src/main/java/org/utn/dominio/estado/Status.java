package org.utn.dominio.estado;

import com.fasterxml.jackson.annotation.JsonValue;
import org.utn.dominio.incidencia.Incident;
import org.utn.utils.constantsExceptions;
public interface Status {
    @JsonValue
    String getStatusName();
    default void assignEmployee(Incident incident) throws Exception {
        String msgException =
                String.format(
                        constantsExceptions.ASSIGN_EMPLOYEE_TRANSITION_ERROR,
                        incident.getStatus().getStatusName()
                );
        throw new Exception(msgException);
    }
    default void confirmIncident(Incident incident) throws Exception {
        String msgException =
                String.format(
                        constantsExceptions.CONFIRM_INCIDENT_TRANSITION_ERROR,
                        incident.getStatus().getStatusName()
                );
        throw new Exception(msgException);
    }
    default void rejectIncident(Incident incident) throws Exception {
        String msgException =
                String.format(
                        constantsExceptions.REJECT_INCIDENT_TRANSITION_ERROR,
                        incident.getStatus().getStatusName()
                );
        throw new Exception(msgException);
    }
    default void startProgress(Incident incident) throws Exception {
        String msgException =
                String.format(
                        constantsExceptions.START_PROGRESS_TRANSITION_ERROR,
                        incident.getStatus().getStatusName())
                ;
        throw new Exception(msgException);
    }
    default void resolveIncident(Incident incident) throws Exception{
        String msgException =
                String.format(
                        constantsExceptions.SOLVE_INCIDENT_TRANSITION_ERROR,
                        incident.getStatus().getStatusName()
                );
        throw new Exception(msgException);
    }
}