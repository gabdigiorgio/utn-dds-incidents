package org.utn.persistence;

import org.utn.domain.AccessibilityFeature;

public interface AccessibilityRepository {

    void update(AccessibilityFeature accessibilityFeature);

    AccessibilityFeature getById(Integer id);
}
