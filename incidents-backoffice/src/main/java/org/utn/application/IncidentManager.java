package org.utn.application;

import javassist.NotFoundException;
import org.jetbrains.annotations.NotNull;
import org.utn.domain.accessibility_feature.AccessibilityFeature;
import org.utn.domain.accessibility_feature.Line;
import org.utn.domain.accessibility_feature.Station;
import org.utn.domain.incident.*;
import org.utn.domain.incident.factory.IncidentFactory;
import org.utn.domain.incident.state.State;
import org.utn.domain.incident.state.StateTransitionException;
import org.utn.domain.incident.IncidentsRepository;
import org.utn.domain.users.User;
import org.utn.presentation.api.dto.requests.EditIncidentRequest;
import org.utn.utils.DateUtils;
import org.utn.utils.exceptions.validator.InvalidCatalogCodeException;
import org.utn.utils.exceptions.validator.InvalidDateException;
import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Consumer;

public class IncidentManager {

    private final IncidentsRepository incidentsRepository;
    private InventoryService inventoryService;

    public IncidentManager(IncidentsRepository incidentRepository, InventoryService inventoryService) {
        this.incidentsRepository = incidentRepository;
        this.inventoryService = inventoryService;
    }

    public Incident getIncident(Integer id) throws NotFoundException {
        Incident incident = incidentsRepository.getById(id);
        return incident;
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
            State state,
            String catalogCode
    ) throws InvalidCatalogCodeException {
        List<Incident> incidents;

        incidents = incidentsRepository.findIncidentsWithPagination(startIndex, pageSize, state, orderBy, catalogCode);
        return incidents;
    }

    public Incident createIncident(
            String catalogCode,
            LocalDate reportDate,
            String description,
            State state,
            String operator,
            User reportedBy,
            LocalDate closingDate,
            String rejectedReason
    ) throws InvalidCatalogCodeException, IOException {
        inventoryService.validateCatalogCode(catalogCode);
        Incident newIncident = new Incident(
                catalogCode,
                reportDate,
                description,
                operator,
                reportedBy,
                closingDate,
                rejectedReason,
                state);
        incidentsRepository.save(newIncident);
        return newIncident;
    }

    public Incident editIncident(Integer id, EditIncidentRequest data) throws InvalidDateException, OperationNotSupportedException {
        Incident incident = incidentsRepository.getById(id);
        if (data.reportDate != null) incident.setReportDate(DateUtils.parseDate(data.reportDate));
        if (data.description != null) incident.setDescription(data.description);
        incidentsRepository.update(incident);
        return incident;
    }

    private Incident performIncidentAction(Integer id, Consumer<Incident> action) throws StateTransitionException {
        Incident incident = incidentsRepository.getById(id);
        action.accept(incident);
        incidentsRepository.update(incident);
        return incident;
    }

    public Incident assignEmployeeIncident(Integer id, String employee) throws StateTransitionException {
        return performIncidentAction(id, incident -> incident.assignEmployee(employee));
    }

    public Incident confirmIncident(Integer id) throws StateTransitionException, IOException {
        Incident incident = incidentsRepository.getById(id);
        var catalogCode = incident.getCatalogCode();
        inventoryService.setAccessibilityFeatureStatus(catalogCode, "inaccessible");
        return performIncidentAction(id, Incident::confirm);
    }

    public Incident startProgressIncident(Integer id) throws StateTransitionException {
        return performIncidentAction(id, Incident::startProgress);
    }

    public Incident resolveIncident(Integer id) throws StateTransitionException, IOException {
        var incident = performIncidentAction(id, Incident::resolveIncident);
        var catalogCode = incident.getCatalogCode();

        if (checkAllIncidentsResolved(catalogCode)) {
            inventoryService.setAccessibilityFeatureStatus(catalogCode, "functional");
        }

        return incident;
    }

    private boolean checkAllIncidentsResolved(String catalogCode) {
        return incidentsRepository.allIncidentsResolved(catalogCode);
    }

    public Incident dismissIncident(Integer id, String rejectedReason) throws StateTransitionException {
        return performIncidentAction(id, incident -> incident.dismiss(rejectedReason));
    }

    public void deleteIncident(Integer id) {
        Incident incident = incidentsRepository.getById(id);
        incidentsRepository.remove(incident);
    }

    public List<AccessibilityFeature> getAccessibilityFeatures(Integer limit, String status, String line, String station) throws IOException {
        return inventoryService.getAccessibilityFeatures(limit, status, line, station);
    }

    public List<Line> getLines() throws IOException {
        return inventoryService.getLines();
    }

    public List<Station> getStationsFromLine(String lineId) throws IOException {
        return inventoryService.getStationsFromLine(lineId);
    }
}

