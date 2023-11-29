package org.utn.persistence.line;

import org.utn.domain.Line;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DbLineRepository implements LineRepository {
    private final EntityManagerFactory entityManagerFactory;

    public DbLineRepository(EntityManagerFactory entityManagerFactory) {
        super();
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Line getById(String id) {
        var entityManager = createEntityManager();
        return entityManager.find(org.utn.domain.Line.class, id);
    }

    @Override
    public List<Line> all() {
        var entityManager = createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Line> cq = cb.createQuery(Line.class);
        Root<Line> root = cq.from(Line.class);

        cq.select(root);

        return entityManager.createQuery(cq).getResultList();
    }


    private EntityManager createEntityManager() {
        return this.entityManagerFactory.createEntityManager();
    }
}
