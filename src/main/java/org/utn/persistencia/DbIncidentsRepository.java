package org.utn.persistencia;

import org.utn.dominio.incidencia.Incidencia;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;


public class DbIncidentsRepository implements RepoIncidencias {
    private static EntityManagerFactory entityManagerFactory;
    public static DbIncidentsRepository instanciaUnica;

    public DbIncidentsRepository() {
        super();
    }

    public static DbIncidentsRepository obtenerInstancia() {
        if (instanciaUnica == null) {
            startEntityManagerFactory();
            instanciaUnica = new DbIncidentsRepository();
        }
        return instanciaUnica;
    }

    public void save(Incidencia incidencia) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(incidencia);
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    @Override
    public void update(Incidencia incidencia) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(incidencia);
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
            Incidencia incidencia = entityManager.find(Incidencia.class, id);
            if (incidencia != null) {
                entityManager.remove(incidencia);
            }
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    @Override
    public Incidencia getById(Integer id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(Incidencia.class, id);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    @Override

    public List<Incidencia> findIncidents(int quantity, String status, String orderBy, String place) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Incidencia> criteriaQuery = criteriaBuilder.createQuery(Incidencia.class);
            Root<Incidencia> root = criteriaQuery.from(Incidencia.class);
            //criteriaQuery.select(root);
            if (status != null) {
                Predicate filtroStatus = criteriaBuilder.equal(root.get("estado").as(String.class), status);
                criteriaQuery.where(filtroStatus);
            }

            if (place != null) {
                Predicate filtroPlace = criteriaBuilder.equal(root.get("codigoCatalogo"), place);
                criteriaQuery.where(filtroPlace);
            }

            if (orderBy != null) {
                if (orderBy.equals("createdAt")) {
                    criteriaQuery.orderBy(criteriaBuilder.asc(root.get("fechaReporte")));
                } else {
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get("fechaReporte")));
                }
            }

            List<Incidencia> resultados = entityManager.createQuery(criteriaQuery).getResultList();

            return filtrarPorCantidad(resultados,quantity);
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
            criteriaQuery.select(criteriaBuilder.count(criteriaQuery.from(Incidencia.class)));

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
        String[] keys = new String[] { "javax.persistence.jdbc.url", "javax.persistence.jdbc.user",
                "javax.persistence.jdbc.password", "javax.persistence.jdbc.driver", "hibernate.hbm2ddl.auto",
                "hibernate.connection.pool_size", "hibernate.show_sql" };
        for (String key : keys) {
            if (env.containsKey(key)) {
                String value = env.get(key);
                configOverrides.put(key, value);
            }
        }
        entityManagerFactory = Persistence.createEntityManagerFactory("db", configOverrides);
    }

    private static List<Incidencia> filtrarPorCantidad(List<Incidencia> lista, int cantidad) {
        if (lista.size() <= cantidad) {
            return lista;
        } else {
            return lista.subList(0, cantidad);
        }
    }
}