package org.utn.application;

import org.utn.domain.AccessibilityFeature;
import org.utn.persistence.AccessibilityRepository;

public class AccessibilityFeatureManager {

    private final AccessibilityRepository accessibilityRepository;

    public AccessibilityFeatureManager(AccessibilityRepository accessibilityRepository) {
        this.accessibilityRepository = accessibilityRepository;
    }

    public AccessibilityFeature getAccessibilityFeature(Integer id) {
        AccessibilityFeature accessibilityFeature = accessibilityRepository.getById(id);
        return accessibilityFeature;
    }

    public AccessibilityFeature updateAccessibilityFeatureStatus(Integer id, AccessibilityFeature.Status status) {
        AccessibilityFeature accessibilityFeature = accessibilityRepository.getById(id);
        accessibilityFeature.setStatus(status);
        accessibilityRepository.update(accessibilityFeature);
        return accessibilityFeature;
    }
}
