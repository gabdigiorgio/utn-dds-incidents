package org.utn.exceptions.validador;

public class FormatoCodigoCatalogInvalidoException extends Exception{
    public FormatoCodigoCatalogInvalidoException(String codigoCatalogo) {
        super(String.format("El formato del codigo de catalogo ingresado [%s] es invalido. Debe tener el formato ####-##", codigoCatalogo));
    }
}
