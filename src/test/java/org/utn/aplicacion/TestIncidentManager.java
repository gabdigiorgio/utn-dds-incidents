package org.utn.aplicacion;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.utn.dominio.IncidentBuilderForTest;
import org.utn.dominio.estado.*;
import org.utn.dominio.incidencia.Incident;
import org.utn.dominio.incidencia.IncidentsRepo;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TestIncidentManager {

    private IncidentsRepo repo = mock(IncidentsRepo.class);
    private IncidentManager manager = new IncidentManager(repo);
    private Incident expectedIncident;

    @Test
    public void mustSaveIncidentIntoRepo() {

        givenExcepectedIncident(new IncidentBuilderForTest().build());

        whenCreateIncident();

        mustSaveIntoRepo();
    }

    @Test
    public void mustCreateCorrectReportedIncident() {

        givenExcepectedIncident(new IncidentBuilderForTest().withStatus(new Reported()).build());

        whenCreateIncident();

        mustCreateIncidentCorrectly();
    }

    @Test
    public void mustCreateCorrectAssignedIncident() {

        givenExcepectedIncident(new IncidentBuilderForTest().withStatus(new Assigned()).build());

        whenCreateIncident();

        mustCreateIncidentCorrectly();
    }

    @Test
    public void mustCreateCorrectConfirmedIncident() {

        givenExcepectedIncident(new IncidentBuilderForTest().withStatus(new Confirmed()).build());

        whenCreateIncident();

        mustCreateIncidentCorrectly();
    }

    @Test
    public void mustCreateCorrectRejectedIncident() {

        givenExcepectedIncident(
                new IncidentBuilderForTest()
                .withStatus(new Rejected())
                .withCloseDate(LocalDate.of(2023, 5, 28))
                .withRejectionReason("Motivo rechazo de prueba")
                .build());

        whenCreateIncident();

        mustCreateIncidentCorrectly();
    }

    @Test
    public void mustCreateInProgressIncident() {

        givenExcepectedIncident(new IncidentBuilderForTest().withStatus(new InProgress()).build());

        whenCreateIncident();

        mustCreateIncidentCorrectly();
    }

    @Test
    public void mustCreateSolvedIncident() {

        givenExcepectedIncident(
                new IncidentBuilderForTest()
                .withStatus(new Solved())
                .withCloseDate(LocalDate.of(2023, 5, 28))
                .build());

        whenCreateIncident();

        mustCreateIncidentCorrectly();
    }

    private void givenExcepectedIncident(Incident expectedIncident) {
        this.expectedIncident = expectedIncident;
    }

    private void whenCreateIncident() {
        manager.createIncident(
                expectedIncident.getCatalogCode().getCode(),
                expectedIncident.getReportDate(),
                expectedIncident.getDescription(),
                expectedIncident.getStatus().getStatusName(),
                expectedIncident.getOperator(),
                expectedIncident.getWhoReported(),
                expectedIncident.getCloseDate(),
                expectedIncident.getRejectionReason());
    }

    private void mustCreateIncidentCorrectly() {
        ArgumentCaptor<Incident> argumentCaptor = ArgumentCaptor.forClass(Incident.class);
        verify(repo).save(argumentCaptor.capture());

        Incident actualIncident = argumentCaptor.getValue();
        Assert.assertEquals(expectedIncident.getCatalogCode(), actualIncident.getCatalogCode());
        Assert.assertEquals(expectedIncident.getReportDate(), actualIncident.getReportDate());
        Assert.assertEquals(expectedIncident.getDescription(), actualIncident.getDescription());
        Assert.assertEquals(expectedIncident.getOperator(), actualIncident.getOperator());
        Assert.assertEquals(expectedIncident.getWhoReported(), actualIncident.getWhoReported());
        Assert.assertEquals(expectedIncident.getCloseDate(), actualIncident.getCloseDate());
        Assert.assertEquals(expectedIncident.getRejectionReason(), actualIncident.getRejectionReason());
        Assert.assertEquals(expectedIncident.getStatusName(), actualIncident.getStatusName());
    }

    private void mustSaveIntoRepo() {
        verify(repo).save(ArgumentMatchers.any());
    }
}
