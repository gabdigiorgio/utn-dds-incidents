package org.utn.application;

public class InvalidEmailException extends RuntimeException{
    public InvalidEmailException() {
        super("The provided email is already registered");
    }
}
