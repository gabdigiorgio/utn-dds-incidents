package org.utn.modules;

import org.utn.persistence.accessibility.DbAccessibilityFeatureRepository;
import org.utn.persistence.line.DbLineRepository;
import org.utn.persistence.station.DbStationRepository;

public class RepositoryFactory {

    public static DbAccessibilityFeatureRepository createCatalogCodeRepository() {
        return new DbAccessibilityFeatureRepository(PersistenceUtils.createEntityManager());
    }
    public static DbLineRepository createLineRepository() {
        return new DbLineRepository(PersistenceUtils.createEntityManager());
    }
    public static DbStationRepository createStationRepository() {
        return new DbStationRepository(PersistenceUtils.createEntityManager());
    }
}
