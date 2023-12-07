package org.utn.application;

import org.jetbrains.annotations.NotNull;
import org.utn.domain.incident.Incident;
import org.utn.domain.incident.IncidentsRepository;
import org.utn.domain.incident.InventoryService;
import org.utn.domain.incident.factory.IncidentFactory;
import org.utn.domain.users.User;
import org.utn.utils.exceptions.validator.InvalidCatalogCodeException;
import java.io.IOException;
import java.time.LocalDate;

public class IncidentMassiveManager {
    private final IncidentsRepository incidentsRepository;
    private InventoryService inventoryService;

    public IncidentMassiveManager(IncidentsRepository incidentRepository, InventoryService inventoryService) {
        this.incidentsRepository = incidentRepository;
        this.inventoryService = inventoryService;
    }

    public Incident createIncident(
            String catalogCode,
            LocalDate reportDate,
            String description,
            String state,
            User operator,
            User reportedBy,
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
                                        User operator,
                                        User reportedBy,
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
