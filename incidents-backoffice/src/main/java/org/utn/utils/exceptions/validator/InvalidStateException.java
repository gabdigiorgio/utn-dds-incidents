package org.utn.utils.exceptions.validator;

public class InvalidStateException extends Exception {
    public InvalidStateException(String state) {
        super(String.format("El estado [%s] no es un estado valido, los estados posibles son: [Reportado,Asignado,Confirmado,Desestimado,En progreso,Solucionado]",state));
    }
}
