package org.utn.modules;

import org.utn.persistence.accessibility.DbAccessibilityFeatureRepository;

public class RepositoryFactory {

    public static DbAccessibilityFeatureRepository createCatalogCodeRepository() {
        return new DbAccessibilityFeatureRepository(PersistenceUtils.createEntityManagerFactory());
    }
}
