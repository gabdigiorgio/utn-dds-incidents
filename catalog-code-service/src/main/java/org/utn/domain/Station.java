package org.utn.domain;

import javax.persistence.*;

@Entity
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    private Line line;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "connection_id")
    private Station connection;

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

    public Station getConnection() {
        return connection;
    }
}
