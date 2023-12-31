package org.utn.presentation.api.dto.responses;

import org.utn.domain.users.User;

public class RegisterResponse {
    private Integer id;
    private String email;
    private String token;
    private String role;

    public RegisterResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.token = user.getToken();
        this.role = user.getRole().toString();
    }

    public String getEmail() {
        return email;
    }
    public String getToken() { return token; }
    public String getRole() {
        return role;
    }
    public Integer getId() { return id; }
}
