package org.utn.modules;

import org.utn.persistence.DbAccessibilityFeatureRepository;
import org.utn.persistence.DbLineRepository;

public class RepositoryFactory {

    public static DbAccessibilityFeatureRepository createCatalogCodeRepository() {
        return new DbAccessibilityFeatureRepository(PersistenceUtils.createEntityManager());
    }
    public static DbLineRepository createLineRepository() {
        return new DbLineRepository(PersistenceUtils.createEntityManager());
    }
}
