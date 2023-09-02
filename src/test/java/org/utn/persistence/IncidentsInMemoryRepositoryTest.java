package org.utn.persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.utn.domain.IncidentBuilderForTest;
import org.utn.domain.incident.StateEnum;
import org.utn.domain.incident.State;
import org.utn.domain.incident.Incident;

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
        givenIncidentWithState(StateEnum.REPORTED);
        givenIncidentWithState(StateEnum.ASSIGNED);
        givenIncidentWithState(StateEnum.CONFIRMED);
        givenIncidentWithState(StateEnum.DISMISSED);
        givenIncidentWithState(StateEnum.IN_PROGRESS);
        givenIncidentWithState(StateEnum.RESOLVED);
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

        List<Incident> filteredIncidents = whenFindByState(StateEnum.REPORTED);

        shouldFilterIncidents(1, filteredIncidents);

        shouldBeIncidentOfState(StateEnum.REPORTED.getClass(), filteredIncidents);
    }

    @Test
    public void shouldFilterOneIncidentByAssignedState(){

        List<Incident> filteredIncidents = whenFindByState(StateEnum.ASSIGNED);

        shouldFilterIncidents(1, filteredIncidents);

        shouldBeIncidentOfState(StateEnum.ASSIGNED.getClass(), filteredIncidents);
    }

    @Test
    public void shouldFilterOneIncidentByConfirmedState(){

        List<Incident> filteredIncidents = whenFindByState(StateEnum.CONFIRMED);

        shouldFilterIncidents(1, filteredIncidents);

        shouldBeIncidentOfState(StateEnum.CONFIRMED.getClass(), filteredIncidents);
    }

    @Test
    public void shouldFilterOneIncidentByDismissedState(){

        List<Incident> filteredIncidents = whenFindByState(StateEnum.DISMISSED);

        shouldFilterIncidents(1, filteredIncidents);

        shouldBeIncidentOfState(StateEnum.DISMISSED.getClass(), filteredIncidents);
    }

    @Test
    public void shouldFilterOneIncidentByInProgressState(){

        List<Incident> filteredIncidents = whenFindByState(StateEnum.IN_PROGRESS);

        shouldFilterIncidents(1, filteredIncidents);

        shouldBeIncidentOfState(StateEnum.IN_PROGRESS.getClass(), filteredIncidents);
    }

    @Test
    public void shouldFilterOneIncidentByResolvedState(){

        List<Incident> filteredIncidents = whenFindByState(StateEnum.RESOLVED);

        shouldFilterIncidents(1, filteredIncidents);

        shouldBeIncidentOfState(StateEnum.RESOLVED.getClass(), filteredIncidents);
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
        return incidentRepo.findIncidents(10, state.getStateName(), null, null);
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

    /*
    @Test
    public void ultimosReportados(){

        assertEquals(repoIncidencias.obtenerIncidencias(3,"ordenarPorMasReciente").size(),3);
    }

    @Test
    public void desdeLaMasVieja(){

        assertEquals(repoIncidencias.obtenerIncidencias(5,"ordenarPorLaMasVieja").size(),4);
    }

    @Test
    public void ordenXestado(){
        //pido 4 incidencias pero solo hay 3 de ese estado
        assertEquals(repoIncidencias.obtenerIncidencias(4,EnumEstado.ASIGNADO).size(),3);

        assertEquals(repoIncidencias.obtenerIncidencias(3,EnumEstado.REPORTADO).size(),1);
    }*/

    /*@Test
    public void ordenXLugar(){

        assertEquals(repoIncidencias.obtenerIncidencias(2,"1533-24").size(),2);
    }*/

}