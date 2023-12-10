package org.utn.presentation.api.dto.responses;

import org.utn.domain.incident.Incidents;

import java.util.List;
import java.util.stream.Collectors;

public class IncidentsResponse {
    private List<IncidentResponse> items;
    private Integer totalCount;

    public IncidentsResponse(Incidents incidents) {
        this.items = incidents.getItems().stream().map(IncidentResponse::new).collect(Collectors.toList());
        this.totalCount = incidents.getTotalCount();
    }

    public List<IncidentResponse> getItems() {
        return items;
    }

    public Integer getTotalCount() {
        return totalCount;
    }
}
