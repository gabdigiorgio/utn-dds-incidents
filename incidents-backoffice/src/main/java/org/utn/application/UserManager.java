package org.utn.application;

import javassist.NotFoundException;
import org.utn.domain.users.User;
import org.utn.domain.users.UsersRepository;

public class UserManager {
    private final UsersRepository UsersRepository;
    public UserManager(UsersRepository usersRepository) {
        this.UsersRepository = usersRepository;
    }

    public User registerUser(String email, String password) {
        return User.newUser(email, password);
    }

    public User registerOperator(String email, String password) {
        return User.newOperator(email, password);
    }

    public User findByEmail(String email)  throws NotFoundException {
        User user = UsersRepository.getByEmail(email);
        return user;
    }

}

