package org.utn.domain.incident.state;

public interface Transitionable {
    void verifyCanTransition(State targetState) throws StateTransitionException;
}
