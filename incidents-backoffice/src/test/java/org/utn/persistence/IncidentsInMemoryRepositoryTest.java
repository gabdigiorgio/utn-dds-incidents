package org.utn.persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.utn.domain.IncidentBuilderForTest;
import org.utn.domain.incident.state.State;
import org.utn.domain.incident.Incident;
import org.utn.persistence.incident.IncidentsInMemoryRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IncidentsInMemoryRepositoryTest {

    private static Incident incident;

    private static List<Incident> incidentsList = new ArrayList<>();

    private static final IncidentsInMemoryRepository incidentRepo = IncidentsInMemoryRepository.getInstance(incidentsList);

    @After
    public void clearIncidentList() {
        incidentsList.clear();
    }

    @Before
    public void loadIncidents() {
        givenIncidentWithState(State.REPORTED);
        givenIncidentWithState(State.ASSIGNED);
        givenIncidentWithState(State.CONFIRMED);
        givenIncidentWithState(State.DISMISSED);
        givenIncidentWithState(State.IN_PROGRESS);
        givenIncidentWithState(State.RESOLVED);
    }

    @Test
    public void shouldBeAbleToSaveOneIncident(){

        clearIncidentList();

        givenIncident(new IncidentBuilderForTest().build());

        whenSaveIncident();

        shouldSaveIncident();

    }

    @Test
    public void shouldFilterOneIncidentByReportedState(){

        List<Incident> filteredIncidents = whenFindByState(State.REPORTED);

        shouldFilterIncidents(1, filteredIncidents);

        shouldBeIncidentOfState(State.REPORTED.getClass(), filteredIncidents);
    }

    @Test
    public void shouldFilterOneIncidentByAssignedState(){

        List<Incident> filteredIncidents = whenFindByState(State.ASSIGNED);

        shouldFilterIncidents(1, filteredIncidents);

        shouldBeIncidentOfState(State.ASSIGNED.getClass(), filteredIncidents);
    }

    @Test
    public void shouldFilterOneIncidentByConfirmedState(){

        List<Incident> filteredIncidents = whenFindByState(State.CONFIRMED);

        shouldFilterIncidents(1, filteredIncidents);

        shouldBeIncidentOfState(State.CONFIRMED.getClass(), filteredIncidents);
    }

    @Test
    public void shouldFilterOneIncidentByDismissedState(){

        List<Incident> filteredIncidents = whenFindByState(State.DISMISSED);

        shouldFilterIncidents(1, filteredIncidents);

        shouldBeIncidentOfState(State.DISMISSED.getClass(), filteredIncidents);
    }

    @Test
    public void shouldFilterOneIncidentByInProgressState(){

        List<Incident> filteredIncidents = whenFindByState(State.IN_PROGRESS);

        shouldFilterIncidents(1, filteredIncidents);

        shouldBeIncidentOfState(State.IN_PROGRESS.getClass(), filteredIncidents);
    }

    @Test
    public void shouldFilterOneIncidentByResolvedState(){

        List<Incident> filteredIncidents = whenFindByState(State.RESOLVED);

        shouldFilterIncidents(1, filteredIncidents);

        shouldBeIncidentOfState(State.RESOLVED.getClass(), filteredIncidents);
    }

    private void givenIncident(Incident incident){
        this.incident = incident;
    }

    private void givenIncidentWithState(State state) {
        incident = new IncidentBuilderForTest().withState(state).build();
        incidentsList.add(incident);
    }

    private void whenSaveIncident(){
        incidentRepo.save(incident);
    }

    private List<Incident> whenFindByState(State state) {
        return incidentRepo.findIncidents(10, state.toString(), null, null);
    }

    private void shouldSaveIncident() {
        assertEquals(1 , incidentsList.size());
    }

    private void shouldFilterIncidents(int quantity, List<Incident> filteredIncidents){
        assertEquals(quantity, filteredIncidents.size());
    }

    private void shouldBeIncidentOfState(Class <? extends State> type, List <Incident> filteredIncidents){
        assertTrue(type.isInstance(filteredIncidents.get(0).getState()));
    }
}