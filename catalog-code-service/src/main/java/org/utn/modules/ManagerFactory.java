package org.utn.modules;

import org.utn.application.AccessibilityFeatureManager;

public class ManagerFactory {

    public static AccessibilityFeatureManager createAccessibilityFeatureManager() {
        return new AccessibilityFeatureManager(RepositoryFactory.createCatalogCodeRepository());
    }
}
