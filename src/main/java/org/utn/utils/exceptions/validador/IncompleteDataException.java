package org.utn.utils.exceptions.validador;

public class IncompleteDataException extends Exception{
    public IncompleteDataException(){
        super("No están todos los campos necesarios completos, [Codigo de catalogo, Fecha de reporte, Descripcion y Estado] debe ser ingresados");
    }
}
