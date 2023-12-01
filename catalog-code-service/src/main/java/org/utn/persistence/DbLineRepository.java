package org.utn.persistence;

import org.utn.domain.Line;
import org.utn.domain.LineRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class DbLineRepository implements LineRepository {
    private EntityManager entityManager;

    public DbLineRepository(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }

    @Override
    public Line getById(String id) {
        return Optional.ofNullable(entityManager.find(Line.class, id)).orElseThrow(()
                -> new EntityNotFoundException("Line not found with id: " + id));
    }

    @Override
    public List<Line> all() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Line> cq = cb.createQuery(Line.class);
        Root<Line> root = cq.from(Line.class);

        cq.select(root);

        return entityManager.createQuery(cq).getResultList();
    }
}
