package org.utn.infrastructure.ok_inventory_service.responses;

public class AccessibilityFeatureInventoryResponse {
    private String catalogCode;
    private String type;
    private String status;
    private String station;
    private String line;
    private String dateSinceInaccessible;

    public String getCatalogCode() {
        return catalogCode;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getStation() {
        return station;
    }

    public String getLine() {
        return line;
    }

    public String getDateSinceInaccessible() {
        return dateSinceInaccessible;
    }
}
