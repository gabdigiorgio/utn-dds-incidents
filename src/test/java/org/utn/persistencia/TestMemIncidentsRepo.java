package org.utn.persistencia;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.utn.dominio.IncidentBuilderForTest;
import org.utn.dominio.estado.*;
import org.utn.dominio.incidencia.Incident;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestMemIncidentsRepo {

    private static Incident incident;

    private static List<Incident> incidentsList = new ArrayList<>();

    private static final MemIncidentsRepo incidentsRepo = MemIncidentsRepo.getInstance(incidentsList);

    @After
    public void clearIncidentList() {
        incidentsList.clear();
    }

    @Before
    public void loadIncidents() {
        givenIncidentWithStatus(new Reported());
        givenIncidentWithStatus(new Assigned());
        givenIncidentWithStatus(new Confirmed());
        givenIncidentWithStatus(new Rejected());
        givenIncidentWithStatus(new InProgress());
        givenIncidentWithStatus(new Solved());
    }

    @Test
    public void mustSaveAnIncident(){

        clearIncidentList();

        givenIncident(new IncidentBuilderForTest().build());

        whenSavingIncident();

        mustSaveIncident();

    }

    @Test
    public void mustFilterAnIncidentByReportedStatus(){

        List<Incident> filteredIncidents = whenFindByStatus(new Reported());

        mustFilterIncidents(1, filteredIncidents);

        mustBeIncidentOfStatus(Reported.class, filteredIncidents);
    }

    @Test
    public void mustFilterAnIncidentByAssignedStatus(){

        List<Incident> filteredIncidents = whenFindByStatus(new Assigned());

        mustFilterIncidents(1, filteredIncidents);

        mustBeIncidentOfStatus(Assigned.class, filteredIncidents);
    }

    @Test
    public void mustFilterAnIncidentByConfirmedStatus(){

        List<Incident> filteredIncidents = whenFindByStatus(new Confirmed());

        mustFilterIncidents(1, filteredIncidents);

        mustBeIncidentOfStatus(Confirmed.class, filteredIncidents);
    }

    @Test
    public void mustFilterAnIncidentByRejectedStatus(){

        List<Incident> filteredIncidents = whenFindByStatus(new Rejected());

        mustFilterIncidents(1, filteredIncidents);

        mustBeIncidentOfStatus(Rejected.class, filteredIncidents);
    }

    @Test
    public void mustFilterAnIncidentByInProgressStatus(){

        List<Incident> filteredIncidents = whenFindByStatus(new InProgress());

        mustFilterIncidents(1, filteredIncidents);

        mustBeIncidentOfStatus(InProgress.class, filteredIncidents);
    }

    @Test
    public void mustFilterAnIncidentBySolvedStatus(){

        List<Incident> filteredIncidents = whenFindByStatus(new Solved());

        mustFilterIncidents(1, filteredIncidents);

        mustBeIncidentOfStatus(Solved.class, filteredIncidents);
    }

    private void givenIncident(Incident incident){
        this.incident = incident;
    }

    private void givenIncidentWithStatus(Status status) {
        incident = new IncidentBuilderForTest().withStatus(status).build();
        incidentsList.add(incident);
    }

    private void whenSavingIncident(){
        incidentsRepo.save(incident);
    }

    private List<Incident> whenFindByStatus(Status status) {
        return incidentsRepo.findIncidents(10, status.getStatusName(), null, null);
    }

    private void mustSaveIncident() {
        assertEquals(1 , incidentsList.size());
    }

    private void mustFilterIncidents(int amount, List<Incident> filteredIncidents){
        assertEquals(amount, filteredIncidents.size());
    }

    private void mustBeIncidentOfStatus(Class <? extends Status> type, List <Incident> filteredIncidents){
        assertTrue(type.isInstance(filteredIncidents.get(0).getStatus()));
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
        assertEquals(repoIncidencias.obtenerIncidencias(4,new Asignado()).size(),3);

        assertEquals(repoIncidencias.obtenerIncidencias(3,new Reportado()).size(),1);
    }*/

    /*@Test
    public void ordenXLugar(){

        assertEquals(repoIncidencias.obtenerIncidencias(2,"1533-24").size(),2);
    }*/

}