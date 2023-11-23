package org.utn.persistence;

import org.utn.domain.AccessibilityFeature;

public interface AccessibilityFeatureRepository {

    void save(AccessibilityFeature accessibilityFeature);

    void update(AccessibilityFeature accessibilityFeature);

    AccessibilityFeature getByCatalogCode(String catalogCode);
}
