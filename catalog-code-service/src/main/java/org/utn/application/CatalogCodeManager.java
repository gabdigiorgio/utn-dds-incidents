package org.utn.application;

import org.utn.domain.AccessibilityFeature;
import org.utn.domain.CatalogCode;
import org.utn.persistence.CatalogCodeRepository;

public class CatalogCodeManager {

    private final CatalogCodeRepository catalogCodeRepository;

    public CatalogCodeManager(CatalogCodeRepository catalogCodeRepository) {
        this.catalogCodeRepository = catalogCodeRepository;
    }

    public CatalogCode getCatalogCode(Integer id) {
        CatalogCode catalogCode = catalogCodeRepository.getById(id);
        return catalogCode;
    }

    public CatalogCode updateAccessibilityFeatureStatus(Integer id, AccessibilityFeature.Status status) {
        CatalogCode catalogCode = catalogCodeRepository.getById(id);
        catalogCode.getAccessibilityFeature().setStatus(status);
        catalogCodeRepository.update(catalogCode);
        return catalogCode;
    }
}
