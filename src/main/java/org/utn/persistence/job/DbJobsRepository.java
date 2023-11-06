package org.utn.persistence.job;

import org.utn.domain.job.Job;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.NotFoundException;

public class DbJobsRepository implements JobsRepository {
    private EntityManagerFactory entityManagerFactory;

    public DbJobsRepository(EntityManagerFactory entityManagerFactory) {
        super();
        this.entityManagerFactory = entityManagerFactory;
    }

    public void save(Job job) {
        var entityManager = createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(job);
        entityManager.getTransaction().commit();
    }

    public Job getById(Integer id) {
        var entityManager = createEntityManager();
        try {
            return entityManager.find(Job.class, id);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException();
        }
    }

    private EntityManager createEntityManager()
    {
        return this.entityManagerFactory.createEntityManager();
    }
}
