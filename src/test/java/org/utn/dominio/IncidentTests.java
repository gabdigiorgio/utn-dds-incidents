package org.utn.dominio;

import org.junit.Test;
import org.utn.dominio.estado.*;
import org.utn.dominio.incidencia.CatalogCode;
import org.utn.dominio.incidencia.Incident;
import org.utn.utils.exceptions.validador.InvalidCatalogCodeFormatException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IncidentTests {
    private Incident incident;
    private final String employee = "Empleado de prueba";

    @Test(expected = InvalidCatalogCodeFormatException.class)
    public void mustReturnInvalidCatalogCodeFormatExceptionByCatalogCode() throws InvalidCatalogCodeFormatException {

        new CatalogCode("123456");
    }

    // Tests de transiciones de estados

    @Test
    public void mustPassFromReportedToAssigned() throws Exception {

        givenIncidentWithStatus(new Reported());

        whenAssignEmployee();

        mustPassTo(Assigned.class);
        mustAssignEmployee();
    }

    @Test
    public void mustPassFromAssignedToConfirmed() throws Exception {

        givenIncidentWithStatus(new Assigned());

        whenConfirmIncident();

        mustPassTo(Confirmed.class);
    }

    @Test
    public void mustPassFromAssignToRejected() throws Exception {

        givenIncidentWithStatus(new Assigned());

        whenRejectIncident();

        mustPassTo(Rejected.class);
    }

    // Tests de transicion de estado: Reportado

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenReportedIncidentIsConfirmed() throws Exception{

        givenIncidentWithStatus(new Reported());

        whenConfirmIncident();
    }

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenReportedIncidentIsStarted() throws Exception{

        givenIncidentWithStatus(new Reported());

        whenStartProgressIncident();
    }

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenSolveAReportedIncident() throws Exception{

        givenIncidentWithStatus(new Reported());

        whenSolveIncident();
    }

    // Tests de transicion de estado: Asignado

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenAssignEmployeeToAssignedIncident() throws Exception{

        givenIncidentWithStatus(new Assigned());

        whenAssignEmployee();
    }

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenStartProgressOnAssignedIncident() throws Exception{

        givenIncidentWithStatus(new Assigned()); // TODO: revisar si puede pasar de asignado -> en progreso

        whenStartProgressIncident();
    }

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenAssignedIncidentIsSolved() throws Exception{

        givenIncidentWithStatus(new Assigned());

        whenSolveIncident();
    }

    // Tests de transicion de estado: Confirmado

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenAnEmployeeIsAssignToAConfirmedIncident() throws Exception{

        givenIncidentWithStatus(new Confirmed());

        whenAssignEmployee();
    }

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenConfirmedIncidentTriesToBeConfirmed() throws Exception{

        givenIncidentWithStatus(new Confirmed());

        whenConfirmIncident();
    }

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenSolvingConfirmedIncident() throws Exception{

        givenIncidentWithStatus(new Confirmed());

        whenSolveIncident();
    }

    // Tests de transicion de estado: Desestimado

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenAssigningEmployeeToARejectedIncident() throws Exception{

        givenIncidentWithStatus(new Rejected());

        whenAssignEmployee();
    }

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenConfirmingRejectedIncident() throws Exception{

        givenIncidentWithStatus(new Rejected());

        whenConfirmIncident();
    }

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenRejectRejectedIncident() throws Exception{

        givenIncidentWithStatus(new Rejected());

        whenRejectIncident();
    }

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenStartsProgressOnRejectedIncident() throws Exception{

        givenIncidentWithStatus(new Rejected());

        whenStartProgressIncident();
    }

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenSolvingRejectedIncident() throws Exception{

        givenIncidentWithStatus(new Rejected());

        whenSolveIncident();
    }

    // Tests de transicion de estado: En Progreso

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenAssigningEmployeeToInProgressIncident() throws Exception{

        givenIncidentWithStatus(new InProgress());

        whenAssignEmployee();
    }

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenConfirmingOnProgressIncident() throws Exception{

        givenIncidentWithStatus(new InProgress());

        whenConfirmIncident();
    }

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenRejectsInProgressIncident() throws Exception{

        givenIncidentWithStatus(new InProgress());

        whenRejectIncident();
    }

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenStartsProgressAInProgressIncident() throws Exception{

        givenIncidentWithStatus(new InProgress());

        whenStartProgressIncident();
    }

    // Tests de transicion de estado: Solucionado

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenAssignsEmployeeToASolvedIncident() throws Exception{

        givenIncidentWithStatus(new Solved());

        whenAssignEmployee();
    }

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenConfirmsSolvedIncident() throws Exception{

        givenIncidentWithStatus(new Solved());

        whenConfirmIncident();
    }

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenRejectsSolvedIncident() throws Exception{

        givenIncidentWithStatus(new Solved());

        whenRejectIncident();
    }

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenStartsProgressOnSolvedIncident() throws Exception{

        givenIncidentWithStatus(new Solved());

        whenStartProgressIncident();
    }

    @Test(expected = Exception.class)
    public void mustReturnExceptionWhenSolvingASolvedIncident() throws Exception{

        givenIncidentWithStatus(new Solved());

        whenSolveIncident();
    }

    private void givenIncidentWithStatus(Status status) {
        incident = new IncidentBuilderForTest().withStatus(status).build();
    }

    private void whenAssignEmployee() throws Exception {
        incident.assignEmployee(employee);
    }

    private void whenConfirmIncident() throws Exception {
        incident.confirmIncident();
    }

    private void whenRejectIncident() throws Exception {
        incident.rejectIncident("");
    }

    private void whenSolveIncident() throws Exception {
        incident.solveIncident();
    }

    private void whenStartProgressIncident() throws Exception {
        incident.startProgress();
    }

    private void mustAssignEmployee() {
        assertEquals(incident.getEmployee(), employee);
    }

    private void mustPassTo(Class<? extends Status> type) {
        assertTrue(type.isInstance(incident.getStatus()));
    }

}
