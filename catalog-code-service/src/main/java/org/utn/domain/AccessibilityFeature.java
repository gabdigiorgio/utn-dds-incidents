package org.utn.domain;

public class AccessibilityFeature {
    private Type type;
    private Status status;
    private Station station;

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
