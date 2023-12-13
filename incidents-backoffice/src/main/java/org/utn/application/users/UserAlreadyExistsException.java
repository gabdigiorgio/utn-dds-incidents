package org.utn.application.users;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("El email ingresado ya está registrado");
    }

}
