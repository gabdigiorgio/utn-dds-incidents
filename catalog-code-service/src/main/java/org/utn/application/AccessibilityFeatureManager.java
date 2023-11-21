package org.utn.application;

import org.utn.domain.AccessibilityFeature;
import org.utn.domain.Station;
import org.utn.persistence.AccessibilityFeatureRepository;

public class AccessibilityFeatureManager {

    private final AccessibilityFeatureRepository accessibilityFeatureRepository;

    public AccessibilityFeatureManager(AccessibilityFeatureRepository accessibilityFeatureRepository) {
        this.accessibilityFeatureRepository = accessibilityFeatureRepository;
    }
    
    public void createAccessibilityFeature(String code, AccessibilityFeature.Type type, AccessibilityFeature.Status status, Station station) {
        accessibilityFeatureRepository.save(new AccessibilityFeature(code, type, status, station));
    }

    public AccessibilityFeature getCatalogCode(Integer id) {
        return accessibilityFeatureRepository.getById(id);
    }

    public AccessibilityFeature updateAccessibilityFeatureStatus(Integer id, AccessibilityFeature.Status status) {
        AccessibilityFeature accessibilityFeature = accessibilityFeatureRepository.getById(id);
        accessibilityFeature.setStatus(status);
        accessibilityFeatureRepository.update(accessibilityFeature);
        return accessibilityFeature;
    }
}
