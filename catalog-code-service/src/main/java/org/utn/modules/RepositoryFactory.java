package org.utn.modules;

import org.utn.persistence.DbCatalogCodeRepository;

public class RepositoryFactory {

    public static DbCatalogCodeRepository createCatalogCodeRepository() {
        return new DbCatalogCodeRepository(PersistenceUtils.createEntityManagerFactory());
    }
}
