package org.utn.domain.users;

public interface UsersRepository {
    void save(User user);
    void update(User user);
    User getByEmail(String email);
    User getByToken(String token);
    boolean userExists(String email);
}
