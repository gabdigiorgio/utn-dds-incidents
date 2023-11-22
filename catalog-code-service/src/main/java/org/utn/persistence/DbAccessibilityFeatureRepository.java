package org.utn.persistence;

import org.utn.domain.AccessibilityFeature;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class DbAccessibilityFeatureRepository implements AccessibilityFeatureRepository {
    private final EntityManagerFactory entityManagerFactory;

    public DbAccessibilityFeatureRepository(EntityManagerFactory entityManagerFactory) {
        super();
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void save(AccessibilityFeature accessibilityFeature) {
        var entityManager = createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(accessibilityFeature);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(AccessibilityFeature accessibilityFeature) {
        var entityManager = createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(accessibilityFeature);
        entityManager.getTransaction().commit();
    }

    @Override
    public AccessibilityFeature getByCatalogCode(String catalogCode) {
        var entityManager = createEntityManager();
        return entityManager.find(AccessibilityFeature.class, catalogCode);
    }

    private EntityManager createEntityManager() {
        return this.entityManagerFactory.createEntityManager();
    }
}
