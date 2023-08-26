package org.utn.utils.exceptions.validator;

public class InvalidDateException extends Exception{
    public InvalidDateException(String date) {
        super(String.format("El formato de la fecha ingresada [%s] es invalido. Debe tener el formato ddmmaaaa",date));
    }
}