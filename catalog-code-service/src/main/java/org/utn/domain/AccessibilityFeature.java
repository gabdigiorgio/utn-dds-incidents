package org.utn.domain;

public class AccessibilityFeature {
    private Type type;
    private Status status;
    private Station station;

    public void setStatus(Status status) {
        this.status = status;
    }

    private enum Type {
        ELEVATOR,
        ESCALATOR,
        RAMP,
        SIGNAGE,
    }

    private enum Status {
        FUNCTIONAL,
        INACCESSIBLE
    }
}
