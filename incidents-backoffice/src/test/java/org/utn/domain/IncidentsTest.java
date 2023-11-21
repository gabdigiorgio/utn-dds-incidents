package org.utn.domain;

import org.junit.Test;
import org.utn.domain.incident.*;
import org.utn.utils.exceptions.validator.InvalidCatalogCodeException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IncidentsTest {
    private Incident incident;
    private final String employee = "Empleado de prueba";

    @Test(expected = InvalidCatalogCodeException.class)
    public void shouldThrowInvalidCatalogCodeExceptionForCatalogCode() throws InvalidCatalogCodeException {

        new CatalogCode("123456");
    }

    // Tests de transiciones de estados

    @Test
    public void shouldBeAbleToTransitionFromReportedToAssigned() throws Exception {

        givenIncidentWithState(StateEnum.REPORTED);

        whenAssignEmployee();

        shouldTransitionTo(StateEnum.ASSIGNED.getClass());
        shouldAssignEmployee();
    }

    @Test
    public void shouldBeAbleToTransitionFromAssignedToConfirmed() throws Exception {

        givenIncidentWithState(StateEnum.ASSIGNED);

        whenConfirmIncident();

        shouldTransitionTo(StateEnum.CONFIRMED.getClass());
    }

    @Test
    public void shouldBeAbleToTransitionFromAssignedToDismissed() throws Exception {

        givenIncidentWithState(StateEnum.ASSIGNED);

        whenDismissIncident("reason");

        shouldTransitionTo(StateEnum.DISMISSED.getClass());
    }

    // Tests de transicion de estado: Reportado

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenConfirmingReportedIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.REPORTED);

        whenConfirmIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenStartingProgressOfReportedIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.REPORTED);

        whenStartProgressIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenResolvingReportedIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.REPORTED);

        whenResolveIncident();
    }

    // Tests de transicion de estado: Asignado

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenAssigningEmployeeToAssignedIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.ASSIGNED);

        whenAssignEmployee();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenStartingProgressOfAssignedIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.ASSIGNED); // TODO: revisar si puede pasar de asignado -> en progreso

        whenStartProgressIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenResolvingAssignedIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.ASSIGNED);

        whenResolveIncident();
    }

    // Tests de transicion de estado: Confirmado

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenAssigningEmployeeToConfirmedIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.CONFIRMED);

        whenAssignEmployee();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenConfirmingConfirmedIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.CONFIRMED);

        whenConfirmIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenResolvingConfirmedIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.CONFIRMED);

        whenResolveIncident();
    }

    // Tests de transicion de estado: Desestimado

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenAssigningEmployeeToDismissedIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.DISMISSED);

        whenAssignEmployee();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenConfirmingDismissedIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.DISMISSED);

        whenConfirmIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenDismissingDismissedIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.DISMISSED);

        whenDismissIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenStartingProgressOfDismissedIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.DISMISSED);

        whenStartProgressIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenResolvingDismissedIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.DISMISSED);

        whenResolveIncident();
    }

    // Tests de transicion de estado: En Progreso

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenAssigningEmployeeToInProgressIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.IN_PROGRESS);

        whenAssignEmployee();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenConfirmingInProgressIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.IN_PROGRESS);

        whenConfirmIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenDismissingInProgressIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.IN_PROGRESS);

        whenDismissIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenStartingProgressOfInProgressIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.IN_PROGRESS);

        whenStartProgressIncident();
    }

    // Tests de transicion de estado: Solucionado

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenAssigningEmployeeToResolvedIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.RESOLVED);

        whenAssignEmployee();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenConfirmingResolvedIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.RESOLVED);

        whenConfirmIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenDismissingResolvedIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.RESOLVED);

        whenDismissIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenStartingProgressOfResolvedIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.RESOLVED);

        whenStartProgressIncident();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenResolvingResolvedIncident() throws StateTransitionException{

        givenIncidentWithState(StateEnum.RESOLVED);

        whenResolveIncident();
    }

    private void givenIncidentWithState(State state) {
        incident = new IncidentBuilderForTest().withState(state).build();
    }

    private void whenAssignEmployee() throws StateTransitionException {
        incident.assignEmployee(employee);
    }

    private void whenConfirmIncident() throws StateTransitionException {
        incident.confirmIncident();
    }

    private void whenDismissIncident() throws IllegalArgumentException, StateTransitionException {
        incident.dismissIncident("");
    }
    private void whenDismissIncident(String reason) throws IllegalArgumentException, StateTransitionException {
        incident.dismissIncident(reason);
    }

    private void whenResolveIncident() throws StateTransitionException {
        incident.resolveIncident();
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
