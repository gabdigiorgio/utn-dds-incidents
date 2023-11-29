package org.utn.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Line {
    @Id
    private String id;
    private String name;
    protected Line() {
        super();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
