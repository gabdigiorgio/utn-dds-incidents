package org.utn.dominio.incidencia.factory;

import org.utn.dominio.estado.*;
import org.utn.dominio.incidencia.CatalogCode;
import org.utn.dominio.incidencia.Incident;

import java.time.LocalDate;

public class IncidentFactory {
    public static Incident createReportedIncident(
            CatalogCode catalogCode,
            LocalDate reportDate,
            String description,
            String operator,
            String whoReported
    )
    {
        Status status = new Reported();
        return
                new Incident(
                        catalogCode,
                        reportDate,
                        description,
                        operator,
                        whoReported,
                        null,
                        "",
                        status
                );
    }

    public static Incident createAssignedIncident(
            CatalogCode catalogCode,
            LocalDate reportDate,
            String description,
            String operator,
            String whoReported,
            LocalDate closeDate,
            String rejectionReason
    )
    {
        Status status = new Assigned();
        return
                new Incident(
                        catalogCode,
                        reportDate,
                        description,
                        operator,
                        whoReported,
                        closeDate,
                        rejectionReason,
                        status
                );
    }

    public static Incident createConfirmedIncident(
            CatalogCode catalogCode,
            LocalDate reportDate,
            String description,
            String operator,
            String whoReported,
            LocalDate closeDate
    )
    {
        Status status = new Confirmed();
        return
                new Incident(
                        catalogCode,
                        reportDate,
                        description,
                        operator,
                        whoReported,
                        closeDate,
                        "",
                        status
                );
    }

    public static Incident createRejectedIncident(
            CatalogCode catalogCode,
            LocalDate reportDate,
            String description,
            String operator,
            String whoReported,
            LocalDate closeDate,
            String rejectionReason
    )
    {
        Status status = new Rejected();
        return
                new Incident(
                        catalogCode,
                        reportDate,
                        description,
                        operator,
                        whoReported,
                        closeDate,
                        rejectionReason,
                        status
                );
    }

    public static Incident createInProgressIncident(
            CatalogCode catalogCode,
            LocalDate reportDate,
            String description,
            String operator,
            String whoReported,
            LocalDate closeDate,
            String rejectionReason
    )
    {
        Status status = new InProgress();
        return
                new Incident(
                        catalogCode,
                        reportDate,
                        description,
                        operator,
                        whoReported,
                        closeDate,
                        rejectionReason,
                        status
                );
    }

    public static Incident createSolvedIncident(
            CatalogCode catalogCode,
            LocalDate reportDate,
            String description,
            String operator,
            String whoReported,
            LocalDate closeDate,
            String rejectionReason
    )
    {
        Status status = new Solved();
        return
                new Incident(
                        catalogCode,
                        reportDate,
                        description,
                        operator,
                        whoReported,
                        closeDate,
                        rejectionReason,
                        status
                );
    }
}