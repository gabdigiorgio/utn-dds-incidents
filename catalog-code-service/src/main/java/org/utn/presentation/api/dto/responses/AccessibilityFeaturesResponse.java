package org.utn.presentation.api.dto.responses;

import org.utn.domain.AccessibilityFeatures;

import java.util.List;
import java.util.stream.Collectors;

public class AccessibilityFeaturesResponse {

    private List<AccessibilityFeatureResponse> items;
    private Integer totalCount;


    public AccessibilityFeaturesResponse(AccessibilityFeatures accessibilityFeatures) {
        this.items = accessibilityFeatures.getItems().stream().map(AccessibilityFeatureResponse::new).collect(Collectors.toList());
        this.totalCount = accessibilityFeatures.getTotalCount();
    }

    public List<AccessibilityFeatureResponse> getItems() {
        return items;
    }

    public Integer getTotalCount() {
        return totalCount;
    }
}
