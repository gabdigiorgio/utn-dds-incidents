package org.utn.domain;

import org.utn.domain.AccessibilityFeature;

import java.util.List;

public interface AccessibilityFeatureRepository {

    void save(AccessibilityFeature accessibilityFeature);

    void update(AccessibilityFeature accessibilityFeature);

    AccessibilityFeature getByCatalogCode(String catalogCode);

    List<AccessibilityFeature> findAccessibilityFeatures(int quantity, String catalogCode, String line, String station,
                                                         AccessibilityFeature.Status status, AccessibilityFeature.Type type);
}
