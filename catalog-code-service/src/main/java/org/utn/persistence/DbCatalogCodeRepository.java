package org.utn.persistence;

import org.utn.domain.CatalogCode;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class DbCatalogCodeRepository implements CatalogCodeRepository {
    private final EntityManagerFactory entityManagerFactory;

    public DbCatalogCodeRepository(EntityManagerFactory entityManagerFactory) {
        super();
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(CatalogCode catalogCode) {
        var entityManager = createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(catalogCode);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(CatalogCode catalogCode) {
        var entityManager = createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(catalogCode);
        entityManager.getTransaction().commit();
    }

    @Override
    public CatalogCode getById(Integer id) {
        var entityManager = createEntityManager();
        return entityManager.find(CatalogCode.class, id);
    }

    private EntityManager createEntityManager() {
        return this.entityManagerFactory.createEntityManager();
    }
}
