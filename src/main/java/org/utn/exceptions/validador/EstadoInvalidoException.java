package org.utn.exceptions.validador;

public class EstadoInvalidoException extends Exception {
    public EstadoInvalidoException(String estado) {
        super(String.format("El estado %s no es un estado valido, los estados validos son: [Reportado,Asignado,Confirmado,Desestimado,En progreso,Solucionado]",estado));
    }
}
