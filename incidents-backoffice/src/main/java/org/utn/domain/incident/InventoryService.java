package org.utn.domain.incident;

import org.utn.domain.accessibility_feature.AccessibilityFeature;
import org.utn.domain.accessibility_feature.Line;
import org.utn.domain.accessibility_feature.Station;

import java.io.IOException;
import java.util.List;

public interface InventoryService {
    void validateCatalogCode(String catalogCode) throws IOException;

    List<AccessibilityFeature> getAccessibilityFeatures(Integer limit, String status, String line, String station) throws IOException;

    List<Line> getLines() throws IOException;

    List<Station> getStationsFromLine(String lineId) throws IOException;
}
