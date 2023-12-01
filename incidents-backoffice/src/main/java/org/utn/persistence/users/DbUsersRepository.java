package org.utn.persistence.users;

import org.utn.domain.users.User;
import org.utn.domain.users.UsersRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.Predicate;
import java.util.List;

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

        List<User> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        return resultList.stream().findFirst().orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    @Override
    public User getByToken(String token) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(User.class);
        var root = criteriaQuery.from(User.class);

        Predicate tokenFilter = criteriaBuilder.equal(root.get("token"), token);
        criteriaQuery.where(tokenFilter);

        List<User> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        return resultList.isEmpty() ? null : resultList.get(0);
    }

    @Override
    public boolean userExists(String email) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(Long.class);
        var root = criteriaQuery.from(User.class);

        criteriaQuery.select(criteriaBuilder.count(root));
        criteriaQuery.where(criteriaBuilder.equal(root.get("email"), email));

        Long count = entityManager.createQuery(criteriaQuery).getSingleResult();
        return count > 0;
    }

}