package org.utn.aplicacion;

import org.jetbrains.annotations.NotNull;
import org.utn.controllers.inputs.ChangeStatus;
import org.utn.controllers.inputs.CreateIncident;
import org.utn.controllers.inputs.EditIncident;
import org.utn.dominio.estado.Reported;
import org.utn.dominio.incidencia.CatalogCode;
import org.utn.dominio.incidencia.Incident;
import org.utn.dominio.incidencia.IncidentsRepo;
import org.utn.dominio.incidencia.factory.IncidentFactory;
import org.utn.utils.DateUtils;
import org.utn.utils.exceptions.validador.InvalidCatalogCodeFormatException;

import java.time.LocalDate;
import java.util.List;

public class IncidentManager {

    private final IncidentsRepo incidentsRepo;

    public IncidentManager(IncidentsRepo incidentsRepo) {
        this.incidentsRepo = incidentsRepo;
    }

    public List<Incident> getIncidents(
        Integer limit, 
        String orderBy,
        String status,
        String place
    ) throws InvalidCatalogCodeFormatException {
        List<Incident> incidents;

        incidents = incidentsRepo.findIncidents(limit, status, orderBy, place);
        return incidents;
    }

    public Incident createIncident(CreateIncident data) throws InvalidCatalogCodeFormatException {
        Incident latestIncident  = newIncident(new CatalogCode(data.code),   // uso latest para demarcar que es la mas nueva
                DateUtils.parseDate(data.reportDate),
                data.description,
                new Reported().getStatusName(),
                null,
                data.reporterId,
                null,
                null);
        incidentsRepo.save(latestIncident );
        return latestIncident ;
    }

    public Incident editIncident(Integer id, EditIncident data) throws Exception {
        Incident incident = incidentsRepo.getIncidentById(id);
        if (incident == null) throw new Exception("INCIDENT_NOT_FOUND");

        // data.status,
        if (data.employeeId != null) incident.setEmployee(data.employeeId);
        if (data.closedDate != null) incident.setCloseDate(DateUtils.parseDate(data.closedDate));
        if (data.rejectionReason != null) incident.setRejectionReason(data.rejectionReason);

        // repoIncidencias
        incidentsRepo.update(incident);
        return incident;
    }

    public Incident updateIncidentStatus(Integer id, ChangeStatus request) throws Exception {
        Incident incident = incidentsRepo.getIncidentById(id);
        if (incident == null) throw new Exception("INCIDENT_NOT_FOUND");

        // Refactor: enhance switch 19/6 -> no hay que hacer más break;
        switch (request.status) {
            case "Asignado" -> incident.assignEmployee(request.employee);
            case "Confirmado" -> incident.confirmIncident();
            case "Desestimado" -> incident.rejectIncident(request.rejectionReason);
            case "EnProgreso" -> incident.startProgress();
            case "Solucionado" -> incident.solveIncident();
            default -> throw new Exception("Estado deseado inválido");
        }

        // repoIncidencias
        incidentsRepo.update(incident);
        return incident;
    }

    public void deleteIncident(Integer id) throws Exception {
        Incident incident = incidentsRepo.getIncidentById(id);
        if (incident == null) throw new Exception("INCIDENT_NOT_FOUND");
        incidentsRepo.remove(id);
    }

    public void createIncident( //to do revisar esto: lo hice void porque no devolvia nada
                                String catalogCode,
                                LocalDate reportDate,
                                String description,
                                String status,
                                String operator,
                                String whoReported,
                                LocalDate closeDate,
                                String rejectionReason
    ) throws InvalidCatalogCodeFormatException
    {
        Incident latestIncident = newIncident(
            new CatalogCode(catalogCode),
            reportDate,
            description,
            status,
            operator,
            whoReported,
            closeDate,
            rejectionReason
        );
        incidentsRepo.save(latestIncident);

    }

    @NotNull
    private static Incident newIncident(CatalogCode catalogCode,
                                        LocalDate reportDate,
                                        String description,
                                        String status,
                                        String operator,
                                        String whoReported,
                                        LocalDate closeDate,
                                        String rejectionReason)
    {
        return switch (status) {
            case "Reportado" ->
                    IncidentFactory.createReportedIncident(catalogCode, reportDate, description, operator, whoReported);
            case "Asignado" ->
                    IncidentFactory.createAssignedIncident(catalogCode, reportDate, description, operator, whoReported, closeDate, rejectionReason);
            case "Confirmado" ->
                    IncidentFactory.createConfirmedIncident(catalogCode, reportDate, description, operator, whoReported, closeDate);
            case "Desestimado" ->
                    IncidentFactory.createRejectedIncident(catalogCode, reportDate, description, operator, whoReported, closeDate, rejectionReason);
            case "En progreso" ->
                    IncidentFactory.createInProgressIncident(catalogCode, reportDate, description, operator, whoReported, closeDate, rejectionReason);
            case "Solucionado" ->
                    IncidentFactory.createSolvedIncident(catalogCode, reportDate, description, operator, whoReported, closeDate, rejectionReason);
            default -> throw new IllegalArgumentException("Estado de incidencia inválido: " + status);
        };
    }
}

