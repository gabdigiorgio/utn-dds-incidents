package org.utn.utils.exceptions.validador;

public class FormatoFechaInvalidaException extends Exception{
    public FormatoFechaInvalidaException(String fecha) {
        super(String.format("El formato de la fecha ingresada [%s] es invalido. Debe tener el formato ddmmaaaa",fecha));
    }
}