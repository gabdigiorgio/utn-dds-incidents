package org.utn.utils.exceptions.validador;

public class FormatoCodigoCatalogoInvalidoException extends RuntimeException{
    public FormatoCodigoCatalogoInvalidoException(String codigoCatalogo) {
        super(String.format("El formato del codigo de catalogo ingresado [%s] es invalido. Debe tener el formato ####-##", codigoCatalogo));
    }
}
