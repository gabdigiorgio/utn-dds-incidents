package org.utn.utils.exceptions.validator;

public class InvalidCatalogCodeException extends RuntimeException{
    public InvalidCatalogCodeException(String catalogCode) {
        super(String.format("El formato del codigo de catalogo ingresado [%s] es invalido.", catalogCode));
    }
}
