package org.utn.presentation.api.dto;

import java.util.List;

public class ErrorResponse {
    public int status;
    public String message;
    public List<String> errors;
}