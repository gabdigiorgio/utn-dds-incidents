package org.utn.infrastructure.bcrypt_password_hasher;

import org.mindrot.jbcrypt.BCrypt;
import org.utn.application.users.PasswordHasher;

public class BCryptPasswordHasher implements PasswordHasher {
    @Override
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
