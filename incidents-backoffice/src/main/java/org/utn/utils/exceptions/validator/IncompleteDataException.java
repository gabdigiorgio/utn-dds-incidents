package org.utn.utils.exceptions.validator;

public class IncompleteDataException extends Exception{
    public IncompleteDataException(){
        super("No están todos los campos necesarios completos, [Codigo de catalogo, Fecha de reporte, Descripcion y Estado] debe ser ingresados");
    }
}
