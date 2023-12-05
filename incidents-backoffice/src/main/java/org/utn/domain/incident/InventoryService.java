package org.utn.domain.incident;

import org.utn.domain.accessibility_feature.AccessibilityFeature;

import java.io.IOException;
import java.util.List;

public interface InventoryService {
    void validateCatalogCode(String catalogCode) throws IOException;

    List<AccessibilityFeature> getInaccessibleAccessibilityFeatures(Integer limit, String line, String station) throws IOException;

    String getLines() throws IOException;

    String getStationsFromLine(String lineId) throws IOException;
}
