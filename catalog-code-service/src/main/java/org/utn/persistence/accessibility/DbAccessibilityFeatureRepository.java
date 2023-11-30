package org.utn.persistence.accessibility;

import org.utn.domain.AccessibilityFeature;
import org.utn.domain.AccessibilityFeatureRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbAccessibilityFeatureRepository implements AccessibilityFeatureRepository {
    private EntityManager entityManager;
    public DbAccessibilityFeatureRepository(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }

    @Override
    public void save(AccessibilityFeature accessibilityFeature) {
        entityManager.getTransaction().begin();
        entityManager.persist(accessibilityFeature);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(AccessibilityFeature accessibilityFeature) {
        entityManager.getTransaction().begin();
        entityManager.merge(accessibilityFeature);
        entityManager.getTransaction().commit();
    }

    @Override
    public AccessibilityFeature getByCatalogCode(String catalogCode) {
        return Optional.ofNullable(entityManager.find(AccessibilityFeature.class, catalogCode)).orElseThrow(()
                -> new EntityNotFoundException("Accessibility feature not found with catalog code: " + catalogCode));
    }

    @Override
    public List<AccessibilityFeature> findAccessibilityFeatures(int quantity, String catalogCode, String line, String stationName,
                                                                AccessibilityFeature.Status status, AccessibilityFeature.Type type) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
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
}
