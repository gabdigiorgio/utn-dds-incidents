package org.utn.application;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException() {
        super("La contraseña es incorrecta");
    }
}
