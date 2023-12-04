package org.utn.application;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("The provided email is already registered");
    }

}
