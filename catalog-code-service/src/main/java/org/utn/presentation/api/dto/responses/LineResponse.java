package org.utn.presentation.api.dto.responses;

import org.utn.domain.Line;

public class LineResponse {
    private String id;
    private String name;

    public LineResponse(Line line) {
        this.id = line.getId();
        this.name = line.getName();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
