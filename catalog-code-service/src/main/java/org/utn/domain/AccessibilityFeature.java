package org.utn.domain;

import javax.persistence.*;

@Entity
public class AccessibilityFeature {
    @Id
    @Column(name = "catalog_code")
    private String catalogCode;
    @Enumerated(EnumType.STRING)
    private Type type;
    @Enumerated(EnumType.STRING)
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
