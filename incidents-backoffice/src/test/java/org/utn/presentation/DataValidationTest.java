package org.utn.presentation;

import org.junit.Test;
import org.utn.presentation.incidents_load.Validator;
import org.utn.utils.exceptions.validator.IncompleteDataException;
import org.utn.utils.exceptions.validator.InvalidDateException;

public class DataValidationTest {
    private String catalogCode = "1234-56";
    private String reportDate = "28052023";
    private String description = "Descripcion de prueba";
    private String state = "Asignado";
    private String operator = "";
    private String reportedBy = "";
    private String closingDate = "";
    private String rejectedReason = "";

    @Test
    public void shouldNotThrowIncompleteDataException() throws Exception {

        whenValidate();
    }
    @Test(expected = IncompleteDataException.class)
    public void shouldThrowIncompleteDataExceptionForCatalogCode() throws Exception {

        givenCatalogCode("");

        whenValidate();
    }

    @Test(expected = IncompleteDataException.class)
    public void shouldThrowIncompleteDataExceptionForDescription() throws Exception {

        givenDescripcion("");

        whenValidate();
    }

    @Test(expected = IncompleteDataException.class)
    public void shouldThrowIncompleteDataExceptionForReportDate() throws Exception {

        givenReportDate("");

        whenValidate();
    }

    @Test(expected = IncompleteDataException.class)
    public void shouldThrowIncompleteDataExceptionForState() throws Exception {

        givenState("");

        whenValidate();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionForReportedStateWithRejectedReason() throws Exception {

        givenState("Reportado");
        givenRejectedReason("Motivo de rechazo");

        whenValidate();
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionForDismissedStateWithoutRejectedReason() throws Exception {

        givenState("Desestimado");

        whenValidate();
    }

    @Test(expected = InvalidDateException.class)
    public void shouldThrowInvalidDateExceptionForReportDate() throws Exception {

        givenReportDate("123");

        whenValidate();
    }

    @Test(expected = InvalidDateException.class)
    public void shouldThrowInvalidDateExceptionForClosingDate() throws Exception {

        givenClosingDate("123");

        whenValidate();
    }

    private void givenCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    private void givenDescripcion(String description) {
        this.description = description;
    }

    private void givenReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    private void givenClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    private void givenState(String state) {
        this.state = state;
    }

    private void givenRejectedReason(String rejectedReason){
        this.rejectedReason = rejectedReason;
    }

    private void whenValidate() throws Exception {
        Validator.validate(catalogCode, reportDate, description, state, operator, reportedBy, closingDate, rejectedReason);
    }
}