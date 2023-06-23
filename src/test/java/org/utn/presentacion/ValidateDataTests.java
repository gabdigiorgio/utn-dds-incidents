package org.utn.presentacion;


import org.junit.Test;
import org.utn.presentacion.carga_incidentes.Validator;
import org.utn.utils.exceptions.validador.IncompleteDataException;
import org.utn.utils.exceptions.validador.InvalidDateFormatException;

public class ValidateDataTests {
    private String catalogCode = "1234-56";
    private String reportDate = "28052023";
    private String description = "Descripcion de prueba";
    private String status = "Asignado";
    private String operator = "";
    private String whoReported = "";
    private String closeDate = "";
    private String rejectionReason = "";

    @Test
    public void mustNotReturnIncompleteDataException() throws Exception {

        whenValidate();
    }
    @Test(expected = IncompleteDataException.class)
    public void mustReturnIncompleteDataExceptionByCatalogCode() throws Exception {

        givenCatalogCode("");

        whenValidate();
    }

    @Test(expected = IncompleteDataException.class)
    public void mustReturnIncompleteDataExceptionByDescription() throws Exception {

        givenDescription("");

        whenValidate();
    }

    @Test(expected = IncompleteDataException.class)
    public void mustReturnIncompleteDataExceptionByReportDate() throws Exception {

        givenReportDate("");

        whenValidate();
    }

    @Test(expected = IncompleteDataException.class)
    public void mustReturnIncompleteDataExceptionByStatus() throws Exception {

        givenStatus("");

        whenValidate();
    }

    @Test(expected = Exception.class)
    public void mustReturnExceptionByReportedStatusWithRejectionMotive() throws Exception {

        givenStatus("Reportado");
        givenRejectionReason("Motivo de rechazo");

        whenValidate();
    }

    @Test(expected = Exception.class)
    public void mustReturnExceptionByRejectedStatusWithoutRejectionMotive() throws Exception {

        givenStatus("Desestimado");

        whenValidate();
    }

    @Test(expected = InvalidDateFormatException.class)
    public void mustReturnInvalidDateFormatExceptionByReportDate() throws Exception {

        givenReportDate("123");

        whenValidate();
    }

    @Test(expected = InvalidDateFormatException.class)
    public void mustReturnInvalidDateFormatExceptionByCloseDate() throws Exception {

        givenCloseDate("123");

        whenValidate();
    }

    private void givenCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    private void givenDescription(String description) {
        this.description = description;
    }

    private void givenReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    private void givenCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    private void givenStatus(String status) {
        this.status = status;
    }

    private void givenRejectionReason(String rejectionReason){
        this.rejectionReason = rejectionReason;
    }

    private void whenValidate() throws Exception {
        Validator.validate(catalogCode, reportDate, description, status, operator, whoReported, closeDate, rejectionReason);
    }
}