package org.utn.utils.exceptions.validador;

public class InvalidDateFormatException extends Exception{
    public InvalidDateFormatException(String date) {
        super(String.format("El formato de la fecha ingresada [%s] es invalido. Debe tener el formato ddmmaaaa",date));
    }
}