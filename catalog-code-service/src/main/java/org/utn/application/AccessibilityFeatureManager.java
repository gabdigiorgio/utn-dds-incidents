package org.utn.application;

import org.utn.domain.AccessibilityFeature;
import org.utn.domain.AccessibilityFeatureRepository;
import org.utn.domain.AccessibilityFeatures;

import java.time.LocalDate;
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
        if (status == AccessibilityFeature.Status.INACCESSIBLE) {
            LocalDate currentDate = LocalDate.now();
            accessibilityFeature.setDateSinceInaccessible(currentDate);
        } else if (status == AccessibilityFeature.Status.FUNCTIONAL) {
            accessibilityFeature.setDateSinceInaccessible(null);
        }
        accessibilityFeature.setStatus(status);
        accessibilityFeatureRepository.update(accessibilityFeature);
        return accessibilityFeature;
    }

}
