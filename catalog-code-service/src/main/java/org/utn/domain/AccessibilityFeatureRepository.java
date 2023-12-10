package org.utn.domain;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public interface AccessibilityFeatureRepository {

    void save(AccessibilityFeature accessibilityFeature);

    void update(AccessibilityFeature accessibilityFeature);

    AccessibilityFeature getByCatalogCode(String catalogCode);

    AccessibilityFeatures findAccessibilityFeatures(Integer limit, String catalogCode, String line, Integer station,
                                                         AccessibilityFeature.Status status, AccessibilityFeature.Type type,
                                                         Integer page, Integer pageSize);
}
