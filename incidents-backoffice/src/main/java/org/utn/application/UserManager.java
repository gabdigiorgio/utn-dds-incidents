package org.utn.application;

import org.utn.domain.users.Role;
import org.utn.domain.users.User;
import org.utn.domain.users.UsersRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.UUID;

public class UserManager {
    private final UsersRepository usersRepository;
    public UserManager(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public String login(String email, String password) throws EntityNotFoundException {
        var user = findByEmail(email);
        validatePassword(password, user);
        user.setToken(generateToken());
        usersRepository.update(user);
        return user.getToken();
    }

    private void checkUserNotExists(String email) {
        if (usersRepository.userExists(email)) throw new UserAlreadyExistsException();
    }

    public User registerUser(String email, String password, String stringRole) {
        checkUserNotExists(email);
        Role role = stringRole == "User" ? Role.USER : Role.OPERATOR;
        var newUser = User.newUser(email, password, role, generateToken());
        usersRepository.save(newUser);
        return newUser;
    }

    private User findByEmail(String email) {
        User user = usersRepository.getByEmail(email);
        return user;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private static void validatePassword(String password, User user) {
        if(!Objects.equals(user.getPassword(), password)) throw new InvalidPasswordException();
    }
}

