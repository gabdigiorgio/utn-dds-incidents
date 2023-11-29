package org.utn.modules;

import org.utn.application.AccessibilityFeatureManager;
import org.utn.application.LineManager;
import org.utn.application.StationManager;

public class ManagerFactory {

    public static AccessibilityFeatureManager createAccessibilityFeatureManager() {
        return new AccessibilityFeatureManager(RepositoryFactory.createCatalogCodeRepository());
    }

    public static LineManager createLineManager() {
        return new LineManager(RepositoryFactory.createLineRepository());
    }

    public static StationManager createStationManager() {
        return new StationManager(RepositoryFactory.createStationRepository());
    }
}
