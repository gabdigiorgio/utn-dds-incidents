package org.utn.application.users;

public interface PasswordHasher {
    String hashPassword(String password);
    boolean checkPassword(String password, String hashedPassword);
}
