package org.utn.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Line {
    @Id
    private String id;
    private String name;
    @OneToMany(mappedBy = "line", fetch = FetchType.LAZY)
    private List<Station> stations;

    protected Line() {
        super();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public List<Station> getStations() {
        return stations;
    }
}
