package org.utn.utils.exceptions.validador;

public class FormatoCodigoCatalogInvalidoException extends RuntimeException{
    public FormatoCodigoCatalogInvalidoException(String codigoCatalogo) {
        super(String.format("El formato del codigo de catalogo ingresado [%s] es invalido. Debe tener el formato ####-##", codigoCatalogo));
    }
}
