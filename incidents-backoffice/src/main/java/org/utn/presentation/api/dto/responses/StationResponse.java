package org.utn.presentation.api.dto.responses;

public class StationResponse {
    private String id;
    private String name;
    private String lineConnection;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLineConnection() {
        return lineConnection;
    }

    public void setLineConnection(String lineConnection) {
        this.lineConnection = lineConnection;
    }
}
