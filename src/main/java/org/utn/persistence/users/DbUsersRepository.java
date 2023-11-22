package org.utn.persistence.users;

import org.utn.domain.users.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import javax.ws.rs.NotFoundException;

public class DbUsersRepository implements UsersRepository {
    private EntityManagerFactory entityManagerFactory;

    public DbUsersRepository(EntityManagerFactory entityManagerFactory) {
        super();
        this.entityManagerFactory = entityManagerFactory;
    }

    public void save(User user) {
        var entityManager = createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public User getByEmail(String email) {
        try {
            var entityManager = createEntityManager();
            var criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
            var criteriaQuery = criteriaBuilder.createQuery(User.class);
            var root = criteriaQuery.from(User.class);

            Predicate emailFilter = criteriaBuilder.equal(root.get("email"), email);
            criteriaQuery.where(emailFilter);

            // TODO: este get rompe
            var user = entityManager.createQuery(criteriaQuery).getSingleResult();
            return user;
        } catch (EntityNotFoundException e) {
            throw new NotFoundException();
        }
    }

    private EntityManager createEntityManager() {
        return this.entityManagerFactory.createEntityManager();
    }

}