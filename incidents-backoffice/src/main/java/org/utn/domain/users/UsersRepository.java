package org.utn.domain.users;

public interface UsersRepository {
    void save(User user);
    void update(User user);
    User getByEmail(String email);
    User getByToken(String token);
    User getById(String id);
    boolean userExists(String email);
}
