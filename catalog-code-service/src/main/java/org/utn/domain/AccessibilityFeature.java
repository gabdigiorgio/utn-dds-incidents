package org.utn.domain;

public class AccessibilityFeature {
    private String code;
    private Type type;
    private Status status;
    private Station station;

    public AccessibilityFeature(String code, Type type, Status status, Station station) {
        this.code = code;
        this.type = type;
        this.status = status;
        this.station = station;
    }

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
