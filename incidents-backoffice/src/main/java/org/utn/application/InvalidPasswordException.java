package org.utn.application;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException() {
        super("La contraseña es inválida");
    }
}
