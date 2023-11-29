package org.utn.modules;

import org.utn.persistence.accessibility.DbAccessibilityFeatureRepository;
import org.utn.persistence.line.DbLineRepository;

public class RepositoryFactory {

    public static DbAccessibilityFeatureRepository createCatalogCodeRepository() {
        return new DbAccessibilityFeatureRepository(PersistenceUtils.createEntityManagerFactory());
    }

    public static DbLineRepository createLineRepository() {
        return new DbLineRepository(PersistenceUtils.createEntityManagerFactory());
    }
}
