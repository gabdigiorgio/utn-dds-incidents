package org.utn.application;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException() {
        super("The provided password is invalid");
    }

    public InvalidPasswordException(String message) {
        super(message);
    }
}
