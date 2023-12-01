package org.utn.persistence.users;

import org.utn.domain.users.User;
import org.utn.domain.users.UsersRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import javax.ws.rs.NotFoundException;

public class DbUsersRepository implements UsersRepository {

    private EntityManager entityManager;

    public DbUsersRepository(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }

    public void save(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(User user) {
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public User getByEmail(String email) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(User.class);
        var root = criteriaQuery.from(User.class);

        Predicate emailFilter = criteriaBuilder.equal(root.get("email"), email);
        criteriaQuery.where(emailFilter);

        var user = entityManager.createQuery(criteriaQuery).getSingleResult();
        return user;
    }

}