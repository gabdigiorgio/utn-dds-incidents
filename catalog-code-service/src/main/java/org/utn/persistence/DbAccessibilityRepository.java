package org.utn.persistence;

import org.utn.domain.AccessibilityFeature;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class DbAccessibilityRepository implements AccessibilityRepository {
    private final EntityManagerFactory entityManagerFactory;

    public DbAccessibilityRepository(EntityManagerFactory entityManagerFactory) {
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
    public AccessibilityFeature getById(Integer id) {
        var entityManager = createEntityManager();
        return entityManager.find(AccessibilityFeature.class, id);
    }

    private EntityManager createEntityManager() {
        return this.entityManagerFactory.createEntityManager();
    }
}
