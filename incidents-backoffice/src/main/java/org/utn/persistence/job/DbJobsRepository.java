package org.utn.persistence.job;

import org.utn.domain.job.Job;
import org.utn.domain.job.JobsRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.NotFoundException;

public class DbJobsRepository implements JobsRepository {
    private EntityManager entityManager;

    public DbJobsRepository(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }

    public void save(Job job) {
        entityManager.getTransaction().begin();
        entityManager.persist(job);
        entityManager.getTransaction().commit();
    }

    public void update(Job job) {
        entityManager.getTransaction().begin();
        entityManager.merge(job);
        entityManager.getTransaction().commit();
    }

    public Job getById(Integer id) {
        try {
            return entityManager.find(Job.class, id);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException();
        }
    }
}
