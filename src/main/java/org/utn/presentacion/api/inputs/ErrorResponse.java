package org.utn.presentacion.api.inputs;

import java.util.List;

public class ErrorResponse {
    public int status;
    public String message;
    public List<String> errors;
}