package org.utn.application;

import javassist.NotFoundException;
import org.jetbrains.annotations.NotNull;
import org.utn.domain.incident.*;
import org.utn.domain.incident.factory.IncidentFactory;
import org.utn.persistence.incident.IncidentsRepository;
import org.utn.presentation.api.dto.ChangeState;
import org.utn.presentation.api.dto.EditIncident;
import org.utn.utils.DateUtils;
import org.utn.utils.exceptions.validator.InvalidCatalogCodeException;
import org.utn.utils.exceptions.validator.InvalidDateException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class IncidentManager {

    private final IncidentsRepository incidentsRepository;
    private InventoryService inventoryService;

    public IncidentManager(IncidentsRepository incidentRepository, InventoryService inventoryService) {
        this.incidentsRepository = incidentRepository;
        this.inventoryService = inventoryService;
    }

    public List<Incident> getIncidents(
            Integer limit,
            String orderBy,
            String state,
            String catalogCode
    ) throws InvalidCatalogCodeException {
        List<Incident> incidents;

        incidents = incidentsRepository.findIncidents(limit, state, orderBy, catalogCode);
        return incidents;
    }

    public List<Incident> getIncidentsWithPagination(
            Integer startIndex,
            Integer pageSize,
            String orderBy,
            String state,
            String catalogCode
    ) throws InvalidCatalogCodeException {
        List<Incident> incidents;

        incidents = incidentsRepository.findIncidentsWithPagination(startIndex, pageSize, state, orderBy, catalogCode);
        return incidents;
    }

    public Incident getIncident(Integer id) throws NotFoundException {
        Incident incident = incidentsRepository.getById(id);
        return incident;
    }

    public String getInaccessibleAccessibilityFeatures(Integer limit, String line, String station) throws IOException {
        return inventoryService.getInaccessibleAccessibilityFeatures(limit, line, station);
    }

    public Incident editIncident(Integer id, EditIncident data) throws NotFoundException, InvalidDateException {
        Incident incident = incidentsRepository.getById(id);
        if (data.reportDate != null) incident.setReportDate(DateUtils.parseDate(data.reportDate));
        if (data.description != null) incident.setDescription(data.description);
        if (data.reporterId != null) incident.setReportedBy(data.reporterId);
        incidentsRepository.update(incident);
        return incident;
    }

    public Incident updateIncidentState(Integer id, ChangeState request) throws StateTransitionException {
        Incident incident = incidentsRepository.getById(id);
        String formattedState = request.state.replaceAll("([a-z])([A-Z])", "$1_$2").toUpperCase();
        try {
            StateEnum nextState = StateEnum.valueOf(formattedState);
            incident.updateState(nextState, request.employee, request.rejectedReason);
            incidentsRepository.update(incident);
            return incident;
        } catch (IllegalArgumentException e) {
            throw new StateTransitionException("Invalid state: " + request.state);
        }
    }

    public void deleteIncident(Integer id) {
        Incident incident = incidentsRepository.getById(id);
        incidentsRepository.remove(incident);
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
    ) throws InvalidCatalogCodeException, IOException {
        inventoryService.validateCatalogCode(catalogCode);
        Incident newIncident = newIncident(
                catalogCode,
                reportDate,
                description,
                state,
                operator,
                reportedBy,
                closingDate,
                rejectedReason
        );
        incidentsRepository.save(newIncident);
        return newIncident;
    }

    @NotNull
    private static Incident newIncident(String catalogCode,
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

