package org.utn.application.users;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException() {
        super("La contraseña es incorrecta");
    }
}
