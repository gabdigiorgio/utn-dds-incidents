package org.utn.application.users;

public class UserNotExistsException extends RuntimeException {

    public UserNotExistsException() {
        super("User not exists");
    }

    public UserNotExistsException(String msg) {
        super(msg);
    }
}
