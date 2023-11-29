package org.utn.persistence.station;

import org.utn.domain.Station;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.List;

public class DbStationRepository implements StationRepository {
    private EntityManager entityManager;
    public DbStationRepository(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }

    @Override
    public Station getById(String id) {
        return entityManager.find(Station.class, id);
    }

    @Override
    public List<Station> findByLineId(String lineId) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Station.class);
        var root = criteriaQuery.from(Station.class);

        Predicate lineFilter = criteriaBuilder.equal(root.get("line").get("id").as(String.class), lineId);
        criteriaQuery.where(lineFilter);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
