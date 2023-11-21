package org.utn.modules;

import org.utn.persistence.DbAccessibilityRepository;

public class RepositoryFactory {

    public static DbAccessibilityRepository createAccessibilityRepository() {
        return new DbAccessibilityRepository(PersistenceUtils.createEntityManagerFactory());
    }
}
