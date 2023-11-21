package org.utn.application;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.utn.domain.IncidentBuilderForTest;
import org.utn.domain.incident.StateEnum;
import org.utn.domain.incident.Incident;
import org.utn.persistence.incident.IncidentsRepository;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class IncidentManagerTest {

    private IncidentsRepository repo = mock(IncidentsRepository.class);
    private IncidentManager manager = new IncidentManager(repo);
    private Incident expectedIncident;

    @Test
    public void shouldSaveIncidentInRepo() {

        givenExpectedIncident(new IncidentBuilderForTest().build());

        whenCreateIncident();

        shouldSaveInRepo();
    }

    @Test
    public void shouldCreateCorrectReportedIncident() {

        givenExpectedIncident(new IncidentBuilderForTest().withState(StateEnum.REPORTED).build());

        whenCreateIncident();

        shouldCreateIncidentCorrectly();
    }

    @Test
    public void shouldCreateCorrectAssignedIncident() {

        givenExpectedIncident(new IncidentBuilderForTest().withState(StateEnum.ASSIGNED).build());

        whenCreateIncident();

        shouldCreateIncidentCorrectly();
    }

    @Test
    public void shouldCreateCorrectConfirmedIncident() {

        givenExpectedIncident(new IncidentBuilderForTest().withState(StateEnum.CONFIRMED).build());

        whenCreateIncident();

        shouldCreateIncidentCorrectly();
    }

    @Test
    public void shouldCreateCorrectDismissedIncident() {

        givenExpectedIncident(new IncidentBuilderForTest()
                .withState(StateEnum.DISMISSED)
                .withClosingDate(LocalDate.of(2023, 5, 28))
                .withRejectedReason("Motivo rechazo de prueba")
                .build());

        whenCreateIncident();

        shouldCreateIncidentCorrectly();
    }

    @Test
    public void shouldCreateCorrectInProgressIncident() {

        givenExpectedIncident(new IncidentBuilderForTest().withState(StateEnum.IN_PROGRESS).build());

        whenCreateIncident();

        shouldCreateIncidentCorrectly();
    }

    @Test
    public void shouldCreateCorrectResolvedIncident() {

        givenExpectedIncident(new IncidentBuilderForTest()
                .withState(StateEnum.RESOLVED)
                .withClosingDate(LocalDate.of(2023, 5, 28))
                .build());

        whenCreateIncident();

        shouldCreateIncidentCorrectly();
    }

    private void givenExpectedIncident(Incident expectedIncident) {
        this.expectedIncident = expectedIncident;
    }

    private void whenCreateIncident() {
        manager.createIncident(expectedIncident.getCatalogCode().getCode(),
                expectedIncident.getReportDate(),
                expectedIncident.getDescription(),
                expectedIncident.getState().getStateName(),
                expectedIncident.getOperator(),
                expectedIncident.getReportedBy(),
                expectedIncident.getClosingDate(),
                expectedIncident.getRejectedReason());
    }

    private void shouldCreateIncidentCorrectly() {
        ArgumentCaptor<Incident> argumentCaptor = ArgumentCaptor.forClass(Incident.class);
        verify(repo).save(argumentCaptor.capture());

        Incident currentIncident = argumentCaptor.getValue();
        Assert.assertEquals(expectedIncident.getCatalogCode(), currentIncident.getCatalogCode());
        Assert.assertEquals(expectedIncident.getReportDate(), currentIncident.getReportDate());
        Assert.assertEquals(expectedIncident.getDescription(), currentIncident.getDescription());
        Assert.assertEquals(expectedIncident.getOperator(), currentIncident.getOperator());
        Assert.assertEquals(expectedIncident.getReportedBy(), currentIncident.getReportedBy());
        Assert.assertEquals(expectedIncident.getClosingDate(), currentIncident.getClosingDate());
        Assert.assertEquals(expectedIncident.getRejectedReason(), currentIncident.getRejectedReason());
        Assert.assertEquals(expectedIncident.getStateName(), currentIncident.getStateName());
    }

    private void shouldSaveInRepo() {
        verify(repo).save(ArgumentMatchers.any());
    }
}
