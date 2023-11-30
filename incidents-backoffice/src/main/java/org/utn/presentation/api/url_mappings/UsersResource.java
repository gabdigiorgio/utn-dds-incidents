package org.utn.presentation.api.url_mappings;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
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
            ApiBuilder.post("/login", usersController.login);
            ApiBuilder.post("/register", usersController.register);
        });
    }
}





