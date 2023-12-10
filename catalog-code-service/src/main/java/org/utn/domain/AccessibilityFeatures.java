package org.utn.domain;

import java.util.List;

public class AccessibilityFeatures {
    private List<AccessibilityFeature> items;
    private Integer totalCount;

    public AccessibilityFeatures(List<AccessibilityFeature> items, Integer totalCount) {
        this.items = items;
        this.totalCount = totalCount;
    }

    public List<AccessibilityFeature> getItems() {
        return items;
    }

    public Integer getTotalCount() {
        return totalCount;
    }
}
