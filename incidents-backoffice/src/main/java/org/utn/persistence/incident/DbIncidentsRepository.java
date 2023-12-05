package org.utn.persistence.incident;

import org.utn.domain.incident.Incident;
import org.utn.domain.incident.IncidentsRepository;
import org.utn.domain.incident.state.State;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class DbIncidentsRepository implements IncidentsRepository {
    private EntityManager entityManager;

    public DbIncidentsRepository(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }

    public void save(Incident incident) {
        entityManager.getTransaction().begin();
        entityManager.persist(incident);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Incident incident) {
        entityManager.getTransaction().begin();
        entityManager.merge(incident);
        entityManager.getTransaction().commit();
    }

    @Override
    public void remove(Incident incident) {
        entityManager.getTransaction().begin();
        entityManager.remove(incident);
        entityManager.getTransaction().commit();
    }

    @Override
    public Incident getById(Integer id) {
        return Optional.ofNullable(entityManager.find(Incident.class, id)).orElseThrow(() -> new EntityNotFoundException("Incident not found with ID: " + id));
    }

    @Override
    public List<Incident> findIncidents(int quantity, String state, String orderBy, String catalogCode) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Incident.class);
        var root = criteriaQuery.from(Incident.class);

        if (state != null) {
            Predicate stateFilter = criteriaBuilder.equal(root.get("state").as(String.class), state);
            criteriaQuery.where(stateFilter);
        }

        if (catalogCode != null) {
            Predicate catalogCodeFilter = criteriaBuilder.equal(root.get("catalogCode"), catalogCode);
            criteriaQuery.where(catalogCodeFilter);
        }

        if (orderBy != null) {
            if (orderBy.equals("createdAt")) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("reportDate")));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("reportDate")));
            }
        }

        var results = entityManager.createQuery(criteriaQuery).setMaxResults(quantity).getResultList();

        return results;
    }

    @Override
    public List<Incident> findIncidentsWithPagination(int startIndex, int pageSize, State state, String orderBy, String catalogCode) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Incident.class);
        var root = criteriaQuery.from(Incident.class);

        if (state != null) {
            Predicate stateFilter = criteriaBuilder.equal(root.get("state"), state);
            criteriaQuery.where(stateFilter);
        }

        if (catalogCode != null) {
            Predicate catalogCodeFilter = criteriaBuilder.equal(root.get("catalogCode"), catalogCode);
            criteriaQuery.where(catalogCodeFilter);
        }

        if (orderBy != null) {
            if (orderBy.equals("createdAt")) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("reportDate")));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("reportDate")));
            }
        }

        TypedQuery<Incident> query = entityManager.createQuery(criteriaQuery);

        query.setFirstResult(startIndex);
        query.setMaxResults(pageSize);

        return query.getResultList();
    }

    @Override
    public int count() {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Incident.class)));

        var count = entityManager.createQuery(criteriaQuery).getSingleResult();

        return count.intValue();
    }

    @Override
    public boolean allIncidentsResolved(String catalogCode) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Incident> root = criteriaQuery.from(Incident.class);

        criteriaQuery.select(criteriaBuilder.count(root))
                .where(
                        criteriaBuilder.equal(root.get("catalogCode"), catalogCode),
                        criteriaBuilder.notEqual(root.get("state"), State.RESOLVED)
                );

        Long unresolvedCount = entityManager.createQuery(criteriaQuery).getSingleResult();

        return unresolvedCount == 0;
    }

}