package org.utn.presentation.api.dto.responses;

import org.utn.domain.users.User;

public class RegisterResponse {
    private String email;
    private String token;

    public RegisterResponse(User user) {
        this.email = user.getEmail();
        this.token = user.getToken();
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}
