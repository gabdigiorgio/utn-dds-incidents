package org.utn.application;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("El email ingresado ya está registrado");
    }

}
