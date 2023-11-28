package org.utn.domain.incident;

public interface Transitionable {
    void verifyCanTransition(State targetState) throws StateTransitionException;
}
