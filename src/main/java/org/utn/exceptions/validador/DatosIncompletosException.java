package org.utn.exceptions.validador;

public class DatosIncompletosException extends Exception{
    public DatosIncompletosException(){
        super("No están todos los campos necesarios completos, [Codigo de catalogo, Fecha de reporte, Descripcion y Estado] debe ser ingresados");
    }
}
