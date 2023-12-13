package org.utn.application.users.exceptions;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException() {
        super("La contraseña es incorrecta");
    }
}
