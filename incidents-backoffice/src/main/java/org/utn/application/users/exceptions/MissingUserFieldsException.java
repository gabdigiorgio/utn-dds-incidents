package org.utn.application.users.exceptions;

public class MissingUserFieldsException extends RuntimeException {
    public MissingUserFieldsException() {
        super("Missing information");
    }

    public MissingUserFieldsException(String msg) {
        super(msg);
    }
}
