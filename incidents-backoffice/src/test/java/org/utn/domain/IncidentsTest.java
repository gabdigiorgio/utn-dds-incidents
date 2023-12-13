package org.utn.domain;

import org.junit.Test;
import org.utn.domain.incident.*;
import org.utn.domain.incident.state.State;
import org.utn.domain.incident.state.StateTransitionException;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IncidentsTest {
    private Incident incident;
    private final String employee = "Empleado de prueba";

    /*@Test(expected = InvalidCatalogCodeException.class)
    public void shouldThrowInvalidCatalogCodeExceptionForCatalogCode() throws InvalidCatalogCodeException {

        new CatalogCode("123456");
    }*/

    // Tests de transiciones de estados

    @Test
    public void shouldBeAbleToTransitionFromReportedToAssigned() throws Exception {

        givenIncidentWithState(State.REPORTED);

        whenAssignEmployee();

        shouldTransitionTo(State.ASSIGNED.getClass());
        shouldAssignEmployee();
    }

    @Test
    public void shouldBeAbleToTransitionFromAssignedToConfirmed() throws Exception {

        givenIncidentWithState(State.ASSIGNED);

        whenConfirmIncident();

        shouldTransitionTo(State.CONFIRMED.getClass());
    }

    @Test
    public void shouldBeAbleToTransitionFromAssignedToDismissed() throws Exception {

        givenIncidentWithState(State.ASSIGNED);

        whenDismissIncident("reason");

        shouldTransitionTo(State.DISMISSED.getClass());
    }

    // Tests de transicion de estado: Reportado

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenConfirmingReportedIncident() throws StateTransitionException {

        givenIncidentWithState(State.REPORTED);

        whenConfirmIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenStartingProgressOfReportedIncident() throws StateTransitionException{

        givenIncidentWithState(State.REPORTED);

        whenStartProgressIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenResolvingReportedIncident() throws StateTransitionException{

        givenIncidentWithState(State.REPORTED);

        whenResolveIncident();
    }

    // Tests de transicion de estado: Asignado

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenAssigningEmployeeToAssignedIncident() throws StateTransitionException{

        givenIncidentWithState(State.ASSIGNED);

        whenAssignEmployee();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenStartingProgressOfAssignedIncident() throws StateTransitionException{

        givenIncidentWithState(State.ASSIGNED); // TODO: revisar si puede pasar de asignado -> en progreso

        whenStartProgressIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenResolvingAssignedIncident() throws StateTransitionException{

        givenIncidentWithState(State.ASSIGNED);

        whenResolveIncident();
    }

    // Tests de transicion de estado: Confirmado

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenAssigningEmployeeToConfirmedIncident() throws StateTransitionException{

        givenIncidentWithState(State.CONFIRMED);

        whenAssignEmployee();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenConfirmingConfirmedIncident() throws StateTransitionException{

        givenIncidentWithState(State.CONFIRMED);

        whenConfirmIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenResolvingConfirmedIncident() throws StateTransitionException{

        givenIncidentWithState(State.CONFIRMED);

        whenResolveIncident();
    }

    // Tests de transicion de estado: Desestimado

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenAssigningEmployeeToDismissedIncident() throws StateTransitionException{

        givenIncidentWithState(State.DISMISSED);

        whenAssignEmployee();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenConfirmingDismissedIncident() throws StateTransitionException{

        givenIncidentWithState(State.DISMISSED);

        whenConfirmIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenDismissingDismissedIncident() throws StateTransitionException{

        givenIncidentWithState(State.DISMISSED);

        whenDismissIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenStartingProgressOfDismissedIncident() throws StateTransitionException{

        givenIncidentWithState(State.DISMISSED);

        whenStartProgressIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenResolvingDismissedIncident() throws StateTransitionException{

        givenIncidentWithState(State.DISMISSED);

        whenResolveIncident();
    }

    // Tests de transicion de estado: En Progreso

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenAssigningEmployeeToInProgressIncident() throws StateTransitionException{

        givenIncidentWithState(State.IN_PROGRESS);

        whenAssignEmployee();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenConfirmingInProgressIncident() throws StateTransitionException{

        givenIncidentWithState(State.IN_PROGRESS);

        whenConfirmIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenDismissingInProgressIncident() throws StateTransitionException{

        givenIncidentWithState(State.IN_PROGRESS);

        whenDismissIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenStartingProgressOfInProgressIncident() throws StateTransitionException{

        givenIncidentWithState(State.IN_PROGRESS);

        whenStartProgressIncident();
    }

    // Tests de transicion de estado: Solucionado

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenAssigningEmployeeToResolvedIncident() throws StateTransitionException{

        givenIncidentWithState(State.RESOLVED);

        whenAssignEmployee();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenConfirmingResolvedIncident() throws StateTransitionException{

        givenIncidentWithState(State.RESOLVED);

        whenConfirmIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenDismissingResolvedIncident() throws StateTransitionException{

        givenIncidentWithState(State.RESOLVED);

        whenDismissIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenStartingProgressOfResolvedIncident() throws StateTransitionException{

        givenIncidentWithState(State.RESOLVED);

        whenStartProgressIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenResolvingResolvedIncident() throws StateTransitionException{

        givenIncidentWithState(State.RESOLVED);

        whenResolveIncident();
    }

    private void givenIncidentWithState(State state) {
        incident = new IncidentBuilderForTest().withState(state).build();
    }

    private void whenAssignEmployee() throws StateTransitionException {
        incident.assignEmployee(employee);
    }

    private void whenConfirmIncident() throws StateTransitionException {
        incident.confirm();
    }

    private void whenDismissIncident() throws IllegalArgumentException, StateTransitionException {
        incident.dismiss("", LocalDate.of(2023, 12, 12));
    }
    private void whenDismissIncident(String reason) throws IllegalArgumentException, StateTransitionException {
        incident.dismiss(reason,  LocalDate.of(2023, 12, 12));
    }

    private void whenResolveIncident() throws StateTransitionException {
        incident.resolveIncident(LocalDate.of(2023, 12, 12));
    }

    private void whenStartProgressIncident() throws StateTransitionException {
        incident.startProgress();
    }

    private void shouldAssignEmployee() {
        assertEquals(incident.getEmployee(), employee);
    }

    private void shouldTransitionTo(Class<? extends State> type) {
        assertTrue(type.isInstance(incident.getState()));
    }

}
