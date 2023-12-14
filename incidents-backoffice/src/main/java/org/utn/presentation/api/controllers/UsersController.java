package org.utn.presentation.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;
import org.utn.application.users.exceptions.MissingUserFieldsException;
import org.utn.domain.users.User;
import org.utn.modules.ManagerFactory;
import org.utn.presentation.api.dto.requests.RegisterUserRequest;
import org.utn.presentation.api.dto.responses.RegisterResponse;

public class UsersController {
    private ObjectMapper objectMapper;

    public UsersController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Handler login = ctx -> {
        var manager = ManagerFactory.createUserManager();

        RegisterUserRequest data = ctx.bodyAsClass(RegisterUserRequest.class);
        User user = manager.login(data.getEmail(), data.getPassword());
        String json = objectMapper.writeValueAsString(new RegisterResponse(user));
        ctx.json(json);
    };

    public Handler registerUser = ctx -> {
        var manager = ManagerFactory.createUserManager();

        RegisterUserRequest data = ctx.bodyAsClass(RegisterUserRequest.class);

        validateField(data.getEmail(), "email");
        validateField(data.getPassword(), "password");

        var user = manager.registerUser(data.getEmail(), data.getPassword());

        String json = objectMapper.writeValueAsString(new RegisterResponse(user));
        ctx.json(json);
    };

    public Handler registerOperator = ctx -> {
        var manager = ManagerFactory.createUserManager();

        RegisterUserRequest data = ctx.bodyAsClass(RegisterUserRequest.class);

        validateField(data.getEmail(), "email");
        validateField(data.getPassword(), "password");

        var user = manager.registerOperator(data.getEmail(), data.getPassword());

        String json = objectMapper.writeValueAsString(new RegisterResponse(user));
        ctx.json(json);
    };

    private static void validateField(String field, String fieldName) {
        if (field == null || field.isEmpty()) {
            throw new MissingUserFieldsException("Falta el campo: " + fieldName);
        }
    }
}