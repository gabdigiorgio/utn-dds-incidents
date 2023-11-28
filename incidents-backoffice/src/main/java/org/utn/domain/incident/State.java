package org.utn.domain.incident;


public enum State implements Transitionable {
    REPORTED {
        @Override
        public void verifyCanTransition(State targetState) throws StateTransitionException {
            if (!targetState.equals(ASSIGNED)) {
                throwTransitionException(targetState);
            }
        }

        @Override
        public String toString() {
            return "Reportado";
        }
    },
    ASSIGNED {
        @Override
        public void verifyCanTransition(State targetState) throws StateTransitionException {
            if (!targetState.equals(CONFIRMED) && !targetState.equals(DISMISSED)) {
                throwTransitionException(targetState);
            }
        }

        @Override
        public String toString() {
            return "Asignado";
        }
    },
    CONFIRMED {
        @Override
        public void verifyCanTransition(State targetState) throws StateTransitionException {
            if (!targetState.equals(IN_PROGRESS)) {
                throwTransitionException(targetState);
            }
        }

        @Override
        public String toString() {
            return "Confirmado";
        }
    },
    DISMISSED {
        @Override
        public void verifyCanTransition(State targetState) throws StateTransitionException {
            throwTransitionException(targetState);
        }

        @Override
        public String toString() {
            return "Desestimado";
        }
    },
    IN_PROGRESS {
        @Override
        public void verifyCanTransition(State targetState) throws StateTransitionException {
            if(!targetState.equals(RESOLVED)) {
                throwTransitionException(targetState);
            }
        }

        @Override
        public String toString() {
            return "En progreso";
        }
    },
    RESOLVED {
        @Override
        public void verifyCanTransition(State targetState) throws StateTransitionException {
            throwTransitionException(targetState);
        }

        @Override
        public String toString() {
            return "Solucionado";
        }
    };

    void throwTransitionException(State targetState) throws StateTransitionException {
        throw new StateTransitionException("Cannot transition from state " +
                this + " to state: " + targetState);
    }

}
