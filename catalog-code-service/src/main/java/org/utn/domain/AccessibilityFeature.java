package org.utn.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AccessibilityFeature {
    @Id
    private String catalogCode;
    private Type type;
    private Status status;
    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    public AccessibilityFeature(String catalogCode, Type type, Status status, Station station) {
        this.catalogCode = catalogCode;
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

    protected AccessibilityFeature() {
        super();
    }
}
