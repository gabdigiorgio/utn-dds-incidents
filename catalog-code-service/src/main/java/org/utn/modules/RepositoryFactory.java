package org.utn.modules;

import org.utn.persistence.DbAccessibilityFeatureRepository;

public class RepositoryFactory {

    public static DbAccessibilityFeatureRepository createCatalogCodeRepository() {
        return new DbAccessibilityFeatureRepository(PersistenceUtils.createEntityManagerFactory());
    }
}
