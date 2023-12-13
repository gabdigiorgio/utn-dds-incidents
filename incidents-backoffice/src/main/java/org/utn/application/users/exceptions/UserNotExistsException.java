package org.utn.application.users.exceptions;

public class UserNotExistsException extends RuntimeException {

    public UserNotExistsException() {
        super("User not exists");
    }

    public UserNotExistsException(String msg) {
        super(msg);
    }
}
