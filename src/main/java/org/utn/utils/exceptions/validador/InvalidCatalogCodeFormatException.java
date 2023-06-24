package org.utn.utils.exceptions.validador;

public class InvalidCatalogCodeFormatException extends RuntimeException{
    public InvalidCatalogCodeFormatException(String catalogCode) {
        super(String.format("El formato del codigo de catalogo ingresado [%s] es invalido. Debe tener el formato ####-##", catalogCode));
    }
}
