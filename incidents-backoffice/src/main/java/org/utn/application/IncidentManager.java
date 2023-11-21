package org.utn.application;

import javassist.NotFoundException;
import org.jetbrains.annotations.NotNull;
import org.utn.domain.incident.CatalogCode;
import org.utn.domain.incident.Incident;
import org.utn.domain.incident.StateEnum;
import org.utn.domain.incident.StateTransitionException;
import org.utn.domain.incident.factory.IncidentFactory;
import org.utn.persistence.incident.IncidentsRepository;
import org.utn.presentation.api.dto.ChangeState;
import org.utn.presentation.api.dto.EditIncident;
import org.utn.utils.DateUtils;
import org.utn.utils.exceptions.validator.InvalidCatalogCodeException;
import org.utn.utils.exceptions.validator.InvalidDateException;
import java.time.LocalDate;
import java.util.List;

public class IncidentManager {

    private final IncidentsRepository IncidentsRepository;

    public IncidentManager(IncidentsRepository incidentRepository) {
        this.IncidentsRepository = incidentRepository;
    }

    public List<Incident> getIncidents(
            Integer limit,
            String orderBy,
            String state,
            String catalogCode
    ) throws InvalidCatalogCodeException {
        List<Incident> incidents;

        incidents = IncidentsRepository.findIncidents(limit, state, orderBy, catalogCode);
        return incidents;
    }

    public Incident getIncident(Integer id) throws NotFoundException {
        Incident incident = IncidentsRepository.getById(id);
        return incident;
    }

    public Incident editIncident(Integer id, EditIncident data) throws NotFoundException, InvalidDateException {
        Incident incident = IncidentsRepository.getById(id);
        if (data.catalogCode != null) incident.catalogCode.setCode(data.catalogCode);
        if (data.reportDate != null) incident.setReportDate(DateUtils.parseDate(data.reportDate));
        if (data.description != null) incident.setDescription(data.description);
        if (data.reporterId != null) incident.setReportedBy(data.reporterId);
        IncidentsRepository.update(incident);
        return incident;
    }

    public Incident updateIncidentState(Integer id, ChangeState request) throws NotFoundException, StateTransitionException {
        Incident incident = IncidentsRepository.getById(id);
        String formattedState = request.state.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
        StateEnum nextState = StateEnum.valueOf(formattedState);
        incident.updateState(nextState, request.employee, request.rejectedReason);
        IncidentsRepository.update(incident);
        return incident;
    }

    public void deleteIncident(Integer id) throws NotFoundException {
        Incident incident = IncidentsRepository.getById(id);
        if (incident == null) throw new NotFoundException("INCIDENT_NOT_FOUND");
        IncidentsRepository.remove(id);
    }

    public Incident createIncident(
            String catalogCode,
            LocalDate reportDate,
            String description,
            String state,
            String operator,
            String reportedBy,
            LocalDate closingDate,
            String rejectedReason
    ) throws InvalidCatalogCodeException {
        Incident newIncident = newIncident(
                new CatalogCode(catalogCode),
                reportDate,
                description,
                state,
                operator,
                reportedBy,
                closingDate,
                rejectedReason
        );
        IncidentsRepository.save(newIncident);
        return newIncident;
    }

    @NotNull
    private static Incident newIncident(CatalogCode catalogCode,
                                        LocalDate reportDate,
                                        String description,
                                        String state,
                                        String operator,
                                        String reportedBy,
                                        LocalDate closingDate,
                                        String rejectedReason) {
        Incident newIncident;
        switch (state) {
            case "Reportado":
                newIncident = IncidentFactory.createReportedIncident(catalogCode, reportDate, description, operator, reportedBy);
                break;
            case "Asignado":
                newIncident = IncidentFactory.createAssignedIncident(catalogCode, reportDate, description, operator, reportedBy, closingDate, rejectedReason);
                break;
            case "Confirmado":
                newIncident = IncidentFactory.createConfirmedIncident(catalogCode, reportDate, description, operator, reportedBy, closingDate);
                break;
            case "Desestimado":
                newIncident = IncidentFactory.createDismissedIncident(catalogCode, reportDate, description, operator, reportedBy, closingDate, rejectedReason);
                break;
            case "En progreso":
                newIncident = IncidentFactory.createInProgressIncident(catalogCode, reportDate, description, operator, reportedBy, closingDate, rejectedReason);
                break;
            case "Solucionado":
                newIncident = IncidentFactory.createResolvedIncident(catalogCode, reportDate, description, operator, reportedBy, closingDate, rejectedReason);
                break;
            default:
                throw new IllegalArgumentException("Estado de incidencia inválido: " + state);
        }
        return newIncident;
    }

}

