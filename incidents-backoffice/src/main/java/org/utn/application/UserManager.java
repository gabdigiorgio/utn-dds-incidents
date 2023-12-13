package org.utn.application;

import org.mindrot.jbcrypt.BCrypt;
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

    public User login(String email, String password) throws EntityNotFoundException {
        var user = findByEmail(email);
        validatePassword(password, user);
        user.setToken(generateToken());
        usersRepository.update(user);
        return user;
    }

    public User registerUser(String email, String password, String stringRole) {
        checkUserNotExists(email);
        Role role = Objects.equals(stringRole, "USER") ? Role.USER : Role.OPERATOR;
        String hashedPassword = hashPassword(password);
        var newUser = User.newUser(email, hashedPassword, role, generateToken());
        usersRepository.save(newUser);
        return newUser;
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private void checkUserNotExists(String email) {
        if (usersRepository.userExists(email)) throw new UserAlreadyExistsException();
    }

    private User findByEmail(String email) {
        User user = usersRepository.getByEmail(email);
        return user;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private static void validatePassword(String password, User user) {
        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new IncorrectPasswordException();
        }
    }
}

