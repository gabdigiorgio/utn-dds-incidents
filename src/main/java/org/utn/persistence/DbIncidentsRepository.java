package org.utn.persistence;

import org.utn.domain.incident.Incident;
import javax.persistence.EntityManager;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.List;

public class DbIncidentsRepository implements IncidentsRepository {
    private EntityManager entityManager;

    public DbIncidentsRepository(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
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
    public void remove(Integer id) {
        entityManager.getTransaction().begin();
        Incident incident = entityManager.find(Incident.class, id);
        if (incident != null) {
            entityManager.remove(incident);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public Incident getById(Integer id) {
        return entityManager.find(Incident.class, id);
    }

    @Override
    public List<Incident> findIncidents(int quantity, String state, String orderBy, String catalogCode) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Incident.class);
        var root = criteriaQuery.from(Incident.class);
        root.fetch("catalogCode", JoinType.LEFT);

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
    public int count() {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Incident.class)));

        var count = entityManager.createQuery(criteriaQuery).getSingleResult();

        return count.intValue();
    }


    private static List<Incident> filterByQuantity(List<Incident> list, int quantity) {
        if (list.size() <= quantity) {
            return list;
        } else {
            return list.subList(0, quantity);
        }
    }
}