package org.utn.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    private Line line;

    @ManyToMany
    @JoinTable(
            name = "station_connections",
            joinColumns = @JoinColumn(name = "station_id"),
            inverseJoinColumns = @JoinColumn(name = "connected_station_id")
    )
    private List<Station> connections;

    protected Station() {
        super();
    }

    public String getName() {
        return name;
    }

    public Line getLine() {
        return line;
    }

    public Integer getId() {
        return id;
    }
}
