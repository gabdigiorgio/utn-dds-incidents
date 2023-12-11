package org.utn.domain.incident;

import java.util.List;

public class Incidents {
    private List<Incident> items;
    private Integer totalCount;

    public Incidents(List<Incident> items, Integer totalCount) {
        this.items = items;
        this.totalCount = totalCount;
    }

    public List<Incident> getItems() {
        return items;
    }

    public Integer getTotalCount() {
        return totalCount;
    }
}
