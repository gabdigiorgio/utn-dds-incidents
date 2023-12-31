package org.utn.presentation.api.url_mappings;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import org.utn.domain.users.Role;
import org.utn.presentation.api.controllers.UsersController;

public class UsersResource implements EndpointGroup {
    ObjectMapper objectMapper;

    public UsersResource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void addEndpoints() {
        UsersController usersController = new UsersController(objectMapper);
        ApiBuilder.path("/api/users", () -> {
            ApiBuilder.post("/", usersController.registerUser, Role.ANYONE);
            ApiBuilder.post("/operator", usersController.registerOperator, Role.OPERATOR);
            ApiBuilder.post("/login", usersController.login, Role.ANYONE);
        });
    }
}





