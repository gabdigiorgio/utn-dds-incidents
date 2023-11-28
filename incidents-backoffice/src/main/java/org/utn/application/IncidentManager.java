package org.utn.application;

import javassist.NotFoundException;
import org.jetbrains.annotations.NotNull;
import org.utn.domain.incident.*;
import org.utn.domain.incident.factory.IncidentFactory;
import org.utn.persistence.incident.IncidentsRepository;
import org.utn.presentation.api.dto.ChangeState;
import org.utn.presentation.api.dto.EditIncidentRequest;
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

    public int getTotalIncidentsCount()  {
        return incidentsRepository.count();
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

    public Incident editIncident(Integer id, EditIncidentRequest data) throws InvalidDateException {
        Incident incident = incidentsRepository.getById(id);
        if (data.reportDate != null) incident.setReportDate(DateUtils.parseDate(data.reportDate));
        if (data.description != null) incident.setDescription(data.description);
        if (data.reporterId != null) incident.setReportedBy(data.reporterId);
        incidentsRepository.update(incident);
        return incident;
    }

    public Incident assignEmployeeIncident(Integer id, String employee) throws StateTransitionException {
        Incident incident = incidentsRepository.getById(id);
        incident.assignEmployee(employee);
        incidentsRepository.update(incident);
        return incident;
    }

    public Incident confirmIncident(Integer id) throws StateTransitionException {
        Incident incident = incidentsRepository.getById(id);
        incident.confirm();
        incidentsRepository.update(incident);
        return incident;
    }

    public Incident startProgressIncident(Integer id) throws StateTransitionException {
        Incident incident = incidentsRepository.getById(id);
        incident.startProgress();
        incidentsRepository.update(incident);
        return incident;
    }

    public Incident resolveIncident(Integer id) throws StateTransitionException {
        Incident incident = incidentsRepository.getById(id);
        incident.resolveIncident();
        incidentsRepository.update(incident);
        return incident;
    }

    public Incident dismissIncident(Integer id, String rejectedReason) throws StateTransitionException {
        Incident incident = incidentsRepository.getById(id);
        incident.dismiss(rejectedReason);
        incidentsRepository.update(incident);
        return incident;
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

