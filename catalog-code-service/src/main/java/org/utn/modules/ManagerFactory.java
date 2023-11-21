package org.utn.modules;

import org.utn.application.CatalogCodeManager;

public class ManagerFactory {

    public static CatalogCodeManager createCatalogCodeManager() {
        return new CatalogCodeManager(RepositoryFactory.createCatalogCodeRepository());
    }
}
