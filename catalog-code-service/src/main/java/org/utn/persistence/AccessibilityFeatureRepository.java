package org.utn.persistence;

import org.utn.domain.AccessibilityFeature;

public interface AccessibilityFeatureRepository {

    void save(AccessibilityFeature catalogCode);

    void update(AccessibilityFeature catalogCode);

    AccessibilityFeature getById(Integer id);
}
