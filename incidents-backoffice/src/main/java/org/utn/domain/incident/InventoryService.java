package org.utn.domain.incident;

import java.io.IOException;

public interface InventoryService {
    void validateCatalogCode(String catalogCode) throws IOException;

    String getInaccessibleAccessibilityFeatures(Integer limit, String line, String station) throws IOException;

    String getLines() throws IOException;

    String getStationsFromLine(String lineId) throws IOException;
}
