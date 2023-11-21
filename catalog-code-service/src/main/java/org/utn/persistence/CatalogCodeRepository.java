package org.utn.persistence;

import org.utn.domain.AccessibilityFeature;
import org.utn.domain.CatalogCode;

public interface CatalogCodeRepository {

    void save(CatalogCode catalogCode);

    void update(CatalogCode catalogCode);

    CatalogCode getById(Integer id);
}
