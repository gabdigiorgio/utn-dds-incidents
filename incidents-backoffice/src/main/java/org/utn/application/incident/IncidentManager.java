package org.utn.application.incident;

import io.javalin.http.ForbiddenResponse;
import javassist.NotFoundException;
import org.utn.domain.accessibility_feature.AccessibilityFeatures;
import org.utn.domain.accessibility_feature.Line;
import org.utn.domain.accessibility_feature.Station;
import org.utn.domain.incident.Incident;
import org.utn.domain.incident.Incidents;
import org.utn.domain.incident.IncidentsRepository;
import org.utn.domain.incident.InventoryService;
import org.utn.domain.incident.state.State;
import org.utn.domain.incident.state.StateTransitionException;
import org.utn.domain.users.Role;
import org.utn.domain.users.User;
import org.utn.utils.DateUtils;
import org.utn.utils.exceptions.validator.InvalidCatalogCodeException;
import org.utn.utils.exceptions.validator.InvalidDateException;

import javax.naming.OperationNotSupportedException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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

    public Incidents getIncidentsWithPagination(
            Integer page,
            Integer pageSize,
            String orderBy,
            State state,
            String catalogCode,
            User reporter
    ) throws InvalidCatalogCodeException {
        Incidents incidents;

        incidents = incidentsRepository.findIncidentsWithPagination(page, pageSize, state, orderBy, catalogCode, reporter);
        return incidents;
    }

    public Incident createReportedIncident(String catalogCode, LocalDate reportDate, String description, User reporter)
            throws InvalidCatalogCodeException, IOException {
        return createIncident(catalogCode, reportDate, description, State.REPORTED, null, reporter,
                null, "");
    }

    public Incident createIncident(
            String catalogCode,
            LocalDate reportDate,
            String description,
            State state,
            User operator,
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

    public Incident editIncident(Integer id, EditIncident data) throws InvalidDateException, OperationNotSupportedException {
        Incident incident = incidentsRepository.getById(id);
        Integer editorId = data.getEditorId();

        if (!(isReporter(editorId, incident) || isOperator(data))) {
            throw new ForbiddenResponse("Solo el reportador o el operador asociado pueden editar los campos de la incidencia");
        }

        if (isInState(incident, State.REPORTED)
                && !(isReporter(editorId, incident) || isOperatorAndReporter(data, incident))) {
            throw new ForbiddenResponse("Solo el reportador puede editar los campos de la incidencia en estado: "
                    + incident.getState().toString());
        }

        if (!isInState(incident, State.REPORTED) && !isAssociatedOperator(editorId, incident)) {
            throw new ForbiddenResponse("Solo el operador asociado puede editar los campos de la incidencia en estado: "
                    + incident.getState().toString());
        }

        if (data.getReportDate() != null) incident.setReportDate(DateUtils.parseDate(data.getReportDate()));
        if (data.getDescription() != null) incident.setDescription(data.getDescription());
        incidentsRepository.update(incident);
        return incident;
    }

    private static boolean isOperatorAndReporter(EditIncident data, Incident incident) {
        return isOperator(data) && isReporter(data.getEditorId(), incident);
    }

    private static boolean isInState(Incident incident, State state) {
        return incident.getState().equals(state);
    }

    private static boolean isOperator(EditIncident data) {
        return data.getEditorRole().equals(Role.OPERATOR);
    }

    private static boolean isReporter(Integer editorId, Incident incident) {
        return Objects.equals(editorId, incident.getReportedBy().getId());
    }

    private static boolean isAssociatedOperator(Integer editorId, Incident incident) {
        return incident.getOperator() == null || Objects.equals(editorId, incident.getOperator().getId());
    }

    private Incident performIncidentAction(Integer id, Consumer<Incident> action) throws StateTransitionException {
        Incident incident = incidentsRepository.getById(id);
        action.accept(incident);
        incidentsRepository.update(incident);
        return incident;
    }

    public Incident setOperator(Integer id, User operator) throws StateTransitionException {
        return performIncidentAction(id, incident -> incident.setOperator(operator));
    }

    public Incident assignEmployeeIncident(Integer id, String employee, Integer operatorId) throws StateTransitionException {
        if (!isAssociatedOperator(operatorId, incidentsRepository.getById(id)))
            throw new ForbiddenResponse("Solo el operador asociado puede realizar el cambio de estado");
        return performIncidentAction(id, incident -> incident.assignEmployee(employee));
    }

    public Incident confirmIncident(Integer id, Integer operatorId) throws StateTransitionException, IOException {
        Incident incident = incidentsRepository.getById(id);

        if (!isAssociatedOperator(operatorId, incident))
            throw new ForbiddenResponse("Solo el operador asociado puede realizar el cambio de estado");

        var catalogCode = incident.getCatalogCode();
        inventoryService.setAccessibilityFeatureStatus(catalogCode, "inaccessible");
        return performIncidentAction(id, Incident::confirm);
    }

    public Incident startProgressIncident(Integer id, Integer operatorId) throws StateTransitionException {
        if (!isAssociatedOperator(operatorId, incidentsRepository.getById(id)))
            throw new ForbiddenResponse("Solo el operador asociado puede realizar el cambio de estado");

        return performIncidentAction(id, Incident::startProgress);
    }

    public Incident resolveIncident(Integer id, Integer operatorId) throws StateTransitionException, IOException {
        if (!isAssociatedOperator(operatorId, incidentsRepository.getById(id)))
            throw new ForbiddenResponse("Solo el operador asociado puede realizar el cambio de estado");

        var incident = performIncidentAction(id, inc -> inc.resolveIncident(LocalDate.now()));

        var catalogCode = incident.getCatalogCode();
        if (checkAllIncidentsResolved(catalogCode)) {
            inventoryService.setAccessibilityFeatureStatus(catalogCode, "functional");
        }

        return incident;
    }

    public Incident dismissIncident(Integer id, String rejectedReason, Integer operatorId) throws StateTransitionException {
        if (!isAssociatedOperator(operatorId, incidentsRepository.getById(id)))
            throw new ForbiddenResponse("Solo el operador asociado puede realizar el cambio de estado");

        return performIncidentAction(id, inc -> inc.dismiss(rejectedReason, LocalDate.now()));
    }

    private boolean checkAllIncidentsResolved(String catalogCode) {
        return incidentsRepository.allIncidentsResolved(catalogCode);
    }

    public void deleteIncident(Integer id) {
        Incident incident = incidentsRepository.getById(id);
        incidentsRepository.remove(incident);
    }

    public AccessibilityFeatures getAccessibilityFeatures(Integer limit, String status, String line, String station,
                                                          Integer page, Integer pageSize) throws IOException {
        return inventoryService.getAccessibilityFeatures(limit, status, line, station, page, pageSize);
    }

    public List<Line> getLines() throws IOException {
        return inventoryService.getLines();
    }

    public List<Station> getStationsFromLine(String lineId) throws IOException {
        return inventoryService.getStationsFromLine(lineId);
    }
}

