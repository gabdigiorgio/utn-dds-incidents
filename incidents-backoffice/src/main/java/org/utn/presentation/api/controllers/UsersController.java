package org.utn.presentation.api.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import javassist.NotFoundException;
import org.utn.domain.users.User;
import org.utn.modules.ManagerFactory;
import org.utn.presentation.api.dto.requests.RegisterUserRequest;
import org.utn.presentation.api.dto.responses.ErrorResponse;
import org.utn.presentation.api.dto.responses.LoginResponse;

import java.util.Collections;

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
        try {
            var manager = ManagerFactory.createUserManager();

            RegisterUserRequest data = ctx.bodyAsClass(RegisterUserRequest.class);

            // Create User
            User newUser = manager.registerUser(data.getEmail(), data.getPassword());
            String json = objectMapper.writeValueAsString(newUser);

            ctx.json(json);
            ctx.status(201);

        } catch (UnrecognizedPropertyException e) {
            handleBadRequest(ctx, e);
        } catch (Exception e) {
            handleInternalError(ctx, e);
        }
    };

    private void handleBadRequest(Context ctx, UnrecognizedPropertyException e) throws JsonProcessingException {
        String message = String.format("Campo desconocido: '%s'", e.getPropertyName());
        ctx.json(parseErrorResponse(400, message));
        ctx.status(400);
    }

    private void handleNotFoundException(Context ctx, NotFoundException notFoundError) throws JsonProcessingException {
        ctx.status(400);
        ctx.json(parseErrorResponse(400, "Invalid credentials"));
    }

    private void handleInternalError(Context ctx, Exception e) throws JsonProcessingException {
        ctx.json(parseErrorResponse(500, e.getMessage()));
        ctx.status(500);
    }

    public static String parseErrorResponse(int statusCode, String errorMsg) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.status = statusCode;
        errorResponse.message = errorMsg;
        errorResponse.errors = Collections.singletonList(errorMsg);

        return objectMapper.writeValueAsString(errorResponse);
    }

}