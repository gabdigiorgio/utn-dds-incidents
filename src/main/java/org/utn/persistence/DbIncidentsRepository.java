package org.utn.persistence;

import org.utn.domain.incident.Incident;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import javax.ws.rs.NotFoundException;
import java.util.List;

public class DbIncidentsRepository implements IncidentsRepository {
    private EntityManagerFactory entityManagerFactory;

    public DbIncidentsRepository(EntityManagerFactory entityManagerFactory) {
        super();
        this.entityManagerFactory = entityManagerFactory;
    }

    public void save(Incident incident) {
        var entityManager = createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(incident);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(Incident incident) {
        var entityManager = createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(incident);
        entityManager.getTransaction().commit();
    }

    @Override
    public void remove(Integer id) {
        var entityManager = createEntityManager();
        entityManager.getTransaction().begin();
        Incident incident = entityManager.find(Incident.class, id);
        if (incident != null) {
            entityManager.remove(incident);
        }
        entityManager.getTransaction().commit();
    }

    @Override
    public Incident getById(Integer id) {
        var entityManager = createEntityManager();
        try {
            return entityManager.find(Incident.class, id);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException();
        }
    }

    @Override
    public List<Incident> findIncidents(int quantity, String state, String orderBy, String catalogCode) {
        var entityManager = createEntityManager();
        var criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
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
    public int count() {
        var entityManager = createEntityManager();
        var criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Long.class);
        criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Incident.class)));

        var count = entityManager.createQuery(criteriaQuery).getSingleResult();

        return count.intValue();
    }

    private EntityManager createEntityManager()
    {
        return this.entityManagerFactory.createEntityManager();
    }

}