package org.utn.presentation.api.dto.responses;

import java.util.List;

public class AccessibilityFeaturesResponse {

    private List<AccessibilityFeatureResponse> items;
    private Integer totalCount;

    public List<AccessibilityFeatureResponse> getItems() {
        return items;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setItems(List<AccessibilityFeatureResponse> items) {
        this.items = items;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
