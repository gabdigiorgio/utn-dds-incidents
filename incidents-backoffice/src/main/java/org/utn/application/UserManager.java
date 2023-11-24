package org.utn.application;

import javassist.NotFoundException;
import org.utn.domain.users.User;
import org.utn.persistence.users.UsersRepository;

public class UserManager {
    private final UsersRepository UsersRepository;
    public UserManager(UsersRepository usersRepository) {
        this.UsersRepository = usersRepository;
    }
    public User registerUser(String email, String password) {
        User newUser = new User(email, password);
        return newUser;
    }
    public User findByEmail(String email)  throws NotFoundException {
        User user = UsersRepository.getByEmail(email);
        return user;
    }

}
