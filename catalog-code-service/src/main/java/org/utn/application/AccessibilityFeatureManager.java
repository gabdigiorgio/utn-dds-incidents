package org.utn.application;

import org.utn.domain.AccessibilityFeature;
import org.utn.domain.AccessibilityFeatureRepository;

import java.util.List;

public class AccessibilityFeatureManager {

    private final AccessibilityFeatureRepository accessibilityFeatureRepository;

    public AccessibilityFeatureManager(AccessibilityFeatureRepository accessibilityFeatureRepository) {
        this.accessibilityFeatureRepository = accessibilityFeatureRepository;
    }

    public AccessibilityFeature getAccessibilityFeature(String catalogCode) {
        return accessibilityFeatureRepository.getByCatalogCode(catalogCode);
    }

    public List<AccessibilityFeature> getAccessibilityFeatures(Integer limit, String catalogCode, String line, String station,
                                                               AccessibilityFeature.Status status, AccessibilityFeature.Type type) {
        var accessibilityFeatures = accessibilityFeatureRepository.findAccessibilityFeatures(limit, catalogCode, line, station, status, type);
        return accessibilityFeatures;
    }

    public AccessibilityFeature updateAccessibilityFeatureStatus(String catalogCode, AccessibilityFeature.Status status) {
        AccessibilityFeature accessibilityFeature = accessibilityFeatureRepository.getByCatalogCode(catalogCode);
        accessibilityFeature.setStatus(status);
        accessibilityFeatureRepository.update(accessibilityFeature);
        return accessibilityFeature;
    }
}
