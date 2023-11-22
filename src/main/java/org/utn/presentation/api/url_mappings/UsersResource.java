package org.utn.presentation.api.url_mappings;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.ApiBuilder;
import io.javalin.apibuilder.EndpointGroup;
import org.utn.application.JobManager;
import org.utn.application.UserManager;
import org.utn.presentation.api.controllers.UsersController;

public class UsersResource implements EndpointGroup {
    UserManager manager;
    JobManager jobManager;
    ObjectMapper objectMapper;

    public UsersResource(UserManager manager, JobManager jobManager, ObjectMapper objectMapper) {
        this.manager = manager;
        this.jobManager = jobManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public void addEndpoints() {
        UsersController usersController = new UsersController(manager, objectMapper);
        ApiBuilder.path("/api/users", () -> {
            ApiBuilder.post("/login", usersController.login);
            ApiBuilder.post("/register", usersController.register);
        });
    }
}





