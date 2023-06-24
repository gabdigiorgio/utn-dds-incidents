package org.utn.utils.exceptions.validador;

public class InvalidStatusException extends Exception {
    public InvalidStatusException(String status) {
        super(String.format("El estado [%s] no es un estado valido, los estados posibles son: [Reportado,Asignado,Confirmado,Desestimado,En progreso,Solucionado]",status));
    }
}
