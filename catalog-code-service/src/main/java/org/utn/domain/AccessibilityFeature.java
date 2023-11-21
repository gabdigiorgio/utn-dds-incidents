package org.utn.domain;

public class AccessibilityFeature {
    private Type type;
    private Status status;
    private Station station;

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Type {
        ELEVATOR, ESCALATOR, RAMP, SIGNAGE,
    }

    public enum Status {
        FUNCTIONAL, INACCESSIBLE
    }
}
