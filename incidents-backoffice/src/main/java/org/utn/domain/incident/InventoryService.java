package org.utn.domain.incident;

import java.io.IOException;

public interface InventoryService {
    void validateCatalogCode(String catalogCode) throws IOException;
}
