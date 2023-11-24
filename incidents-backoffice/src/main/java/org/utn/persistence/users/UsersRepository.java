package org.utn.persistence.users;

import org.utn.domain.users.User;

public interface UsersRepository {
    void save(User user);
    User getByEmail(String email);
}
