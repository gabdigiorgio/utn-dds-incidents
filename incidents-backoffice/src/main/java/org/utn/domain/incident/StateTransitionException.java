package org.utn.domain.incident;
public class StateTransitionException extends RuntimeException {
    public StateTransitionException(String message) {
        super(message);
    }
}
