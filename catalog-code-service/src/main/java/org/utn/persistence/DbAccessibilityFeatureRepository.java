package org.utn.persistence;

import org.utn.domain.AccessibilityFeature;
import org.utn.domain.AccessibilityFeatureRepository;
import org.utn.domain.AccessibilityFeatures;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
    public AccessibilityFeatures findAccessibilityFeatures(Integer limit, String catalogCode, String line, Integer stationId,
                                                                 AccessibilityFeature.Status status, AccessibilityFeature.Type type,
                                                                 Integer page, Integer pageSize) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(AccessibilityFeature.class);
        var root = criteriaQuery.from(AccessibilityFeature.class);

        List<Predicate> predicates = new ArrayList<>();

        if (catalogCode != null) {
            predicates.add(criteriaBuilder.equal(root.get("catalogCode"), catalogCode));
        }

        if (line != null) {
            predicates.add(criteriaBuilder.equal(root.get("station").get("line").get("id"), line));
        }

        if (stationId != null) {
            predicates.add(criteriaBuilder.equal(root.get("station").get("id"), stationId));
        }

        if (status != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), status));
        }

        if (type != null) {
            predicates.add(criteriaBuilder.equal(root.get("type"), type));
        }

        if (limit != null) {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
            var results = entityManager.createQuery(criteriaQuery).setMaxResults(limit).getResultList();
            var count = count(criteriaBuilder, predicates);
            return new AccessibilityFeatures(results, count);
        } else if (page != null && pageSize != null) {
            int startIndex = (page - 1) * pageSize;
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
            var query = entityManager.createQuery(criteriaQuery);
            query.setFirstResult(startIndex);
            query.setMaxResults(pageSize);
            var results = query.getResultList();
            var count = count(criteriaBuilder, predicates);
            return new AccessibilityFeatures(results, count);
        } else {
            criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
            var results = entityManager.createQuery(criteriaQuery).getResultList();
            var count = count(criteriaBuilder, predicates);
            return new AccessibilityFeatures(results, count);
        }
    }

    private int count(CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        var criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(AccessibilityFeature.class)));
        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));
        var count = entityManager.createQuery(criteriaQuery).getSingleResult();
        return count.intValue();
    }
}
