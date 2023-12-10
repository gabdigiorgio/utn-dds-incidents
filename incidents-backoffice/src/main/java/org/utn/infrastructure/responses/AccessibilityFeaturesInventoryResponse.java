package org.utn.infrastructure.responses;

import java.util.List;

public class AccessibilityFeaturesInventoryResponse {
    private List<AccessibilityFeatureInventoryResponse> items;
    private Integer totalCount;

    public AccessibilityFeaturesInventoryResponse() {
    }

    public List<AccessibilityFeatureInventoryResponse> getItems() {
        return items;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setItems(List<AccessibilityFeatureInventoryResponse> items) {
        this.items = items;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
