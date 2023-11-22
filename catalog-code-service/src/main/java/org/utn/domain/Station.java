package org.utn.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String stationName;
    private String line;

    @OneToMany(mappedBy = "sourceStation")
    private List<Connection> sourceConnections;

    @OneToMany(mappedBy = "destinationStation")
    private List<Connection> destinationConnections;

    @OneToMany(mappedBy = "station")
    private List<AccessibilityFeature> accessibilityFeatures;

    protected Station() {
        super();
    }
}
