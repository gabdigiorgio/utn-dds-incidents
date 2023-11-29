package org.utn.persistence.accessibility;

import org.utn.domain.AccessibilityFeature;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<AccessibilityFeature> findAccessibilityFeatures(int quantity, String catalogCode, String line, String stationName,
                                                                AccessibilityFeature.Status status, AccessibilityFeature.Type type) {
        var entityManager = createEntityManager();
        var criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(AccessibilityFeature.class);
        var root = criteriaQuery.from(AccessibilityFeature.class);

        List<Predicate> predicates = new ArrayList<>();

        if (catalogCode != null) {
            predicates.add(criteriaBuilder.equal(root.get("catalogCode"), catalogCode));
        }

        if (line != null) {
            predicates.add(criteriaBuilder.equal(root.get("station").get("line"), line));
        }

        if (stationName != null) {
            predicates.add(criteriaBuilder.equal(root.get("station").get("name"), stationName));
        }

        if (status != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), status));
        }

        if (type != null) {
            predicates.add(criteriaBuilder.equal(root.get("type"), type));
        }

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        var results = entityManager.createQuery(criteriaQuery).setMaxResults(quantity).getResultList();

        return results;
    }


    private EntityManager createEntityManager() {
        return this.entityManagerFactory.createEntityManager();
    }
}
