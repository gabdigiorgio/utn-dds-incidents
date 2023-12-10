package org.utn.application;

import org.utn.domain.AccessibilityFeature;
import org.utn.domain.AccessibilityFeatureRepository;
import org.utn.domain.AccessibilityFeatures;

import java.util.List;

public class AccessibilityFeatureManager {

    private final AccessibilityFeatureRepository accessibilityFeatureRepository;

    public AccessibilityFeatureManager(AccessibilityFeatureRepository accessibilityFeatureRepository) {
        this.accessibilityFeatureRepository = accessibilityFeatureRepository;
    }

    public AccessibilityFeature getAccessibilityFeature(String catalogCode) {
        return accessibilityFeatureRepository.getByCatalogCode(catalogCode);
    }

    public AccessibilityFeatures getAccessibilityFeatures(Integer limit, String catalogCode, String line, Integer stationId,
                                                                AccessibilityFeature.Status status, AccessibilityFeature.Type type,
                                                                Integer page, Integer pageSize) {
        var accessibilityFeatures = accessibilityFeatureRepository.findAccessibilityFeatures(limit, catalogCode, line,
                stationId, status, type,
                page, pageSize);
        return accessibilityFeatures;
    }

    public AccessibilityFeature updateAccessibilityFeatureStatus(String catalogCode, AccessibilityFeature.Status status) {
        AccessibilityFeature accessibilityFeature = accessibilityFeatureRepository.getByCatalogCode(catalogCode);
        accessibilityFeature.setStatus(status);
        accessibilityFeatureRepository.update(accessibilityFeature);
        return accessibilityFeature;
    }

}
