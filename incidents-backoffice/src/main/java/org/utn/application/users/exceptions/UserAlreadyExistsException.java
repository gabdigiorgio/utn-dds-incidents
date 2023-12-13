package org.utn.application.users.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("El email ingresado ya está registrado");
    }

}
