package org.utn.application;

public class UserNotExistsException extends RuntimeException {

    public UserNotExistsException() {
        super("User not exists");
    }

    public UserNotExistsException(String msg) {
        super(msg);
    }
}
