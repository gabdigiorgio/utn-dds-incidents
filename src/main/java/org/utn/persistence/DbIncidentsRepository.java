package org.utn.persistence;

import org.utn.domain.incident.Incident;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DbIncidentsRepository implements IncidentsRepository {
    private static EntityManagerFactory entityManagerFactory;
    public static DbIncidentsRepository uniqueInstance;

    public DbIncidentsRepository() {
        super();
    }

    public static DbIncidentsRepository getInstance() {
        if (uniqueInstance == null) {
            startEntityManagerFactory();
            uniqueInstance = new DbIncidentsRepository();
        }
        return uniqueInstance;
    }

    public void save(Incident incident) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(incident);
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    @Override
    public void update(Incident incident) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(incident);
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    @Override
    public void remove(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Incident incident = entityManager.find(Incident.class, id);
            if (incident != null) {
                entityManager.remove(incident);
            }
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    @Override
    public Incident getById(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Incident.class, id);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    @Override

    public List<Incident> findIncidents(int quantity, String state, String orderBy, String catalogCode) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Incident> criteriaQuery = criteriaBuilder.createQuery(Incident.class);
            Root<Incident> root = criteriaQuery.from(Incident.class);
            //criteriaQuery.select(root);
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

            List<Incident> results = entityManager.createQuery(criteriaQuery).getResultList();

            return filterByQuantity(results, quantity);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    @Override
    public int count() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Incident.class)));

            Long count = entityManager.createQuery(criteriaQuery).getSingleResult();

            return count.intValue();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    private static void startEntityManagerFactory() {
        // https://stackoverflow.com/questions/8836834/read-environment-variables-in-persistence-xml-file
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<String, Object>();
        String[] keys = new String[]{"javax.persistence.jdbc.url", "javax.persistence.jdbc.user",
                "javax.persistence.jdbc.password", "javax.persistence.jdbc.driver", "hibernate.hbm2ddl.auto",
                "hibernate.connection.pool_size", "hibernate.show_sql"};
        for (String key : keys) {
            if (env.containsKey(key)) {
                String value = env.get(key);
                configOverrides.put(key, value);
            }
        }
        entityManagerFactory = Persistence.createEntityManagerFactory("db", configOverrides);
    }

    private static List<Incident> filterByQuantity(List<Incident> list, int quantity) {
        if (list.size() <= quantity) {
            return list;
        } else {
            return list.subList(0, quantity);
        }
    }
}