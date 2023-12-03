package org.utn.presentation.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;
import org.utn.application.MissingUserFieldsException;
import org.utn.modules.ManagerFactory;
import org.utn.presentation.api.dto.requests.RegisterUserRequest;
import org.utn.presentation.api.dto.responses.LoginResponse;
import org.utn.presentation.api.dto.responses.RegisterResponse;

public class UsersController {
    private ObjectMapper objectMapper;

    public UsersController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Handler login = ctx -> {
        var manager = ManagerFactory.createUserManager();

        RegisterUserRequest data = ctx.bodyAsClass(RegisterUserRequest.class);
        var token = manager.login(data.getEmail(), data.getPassword());

        String json = objectMapper.writeValueAsString(new LoginResponse(token));
        ctx.json(json);
    };

    public Handler register = ctx -> {
        var manager = ManagerFactory.createUserManager();

        RegisterUserRequest data = ctx.bodyAsClass(RegisterUserRequest.class);

        validateField(data.getEmail(), "email");
        validateField(data.getPassword(), "password");
        validateField(data.getRole(), "role");

        var user = manager.registerUser(data.getEmail(), data.getPassword(), data.getRole());

        String json = objectMapper.writeValueAsString(new RegisterResponse(user));
        ctx.json(json);
    };

    private static void validateField(String field, String fieldName) {
        if (field == null) {
            throw new MissingUserFieldsException("Missing " + fieldName + " field");
        }
    }
}