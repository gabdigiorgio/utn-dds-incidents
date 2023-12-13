package org.utn.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "accessibility_feature")
public class AccessibilityFeature {
    @Id
    @Column(name = "catalog_code")
    private String catalogCode;
    @Enumerated(EnumType.STRING)
    private Type type;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;
    private LocalDate dateSinceInaccessible;

    public AccessibilityFeature(String catalogCode, Type type, Status status, Station station) {
        this.catalogCode = catalogCode;
        this.type = type;
        this.status = status;
        this.station = station;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCatalogCode() {
        return catalogCode;
    }

    public Type getType() {
        return type;
    }

    public Status getStatus() {
        return status;
    }

    public Station getStation() {
        return station;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate getDateSinceInaccessible() {
        return dateSinceInaccessible;
    }

    public void setDateSinceInaccessible(LocalDate dateSinceInaccessible) {
        this.dateSinceInaccessible = dateSinceInaccessible;
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
