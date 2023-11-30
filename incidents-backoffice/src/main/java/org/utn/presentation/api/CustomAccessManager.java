package org.utn.presentation.api;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.security.AccessManager;
import io.javalin.security.RouteRole;
import org.jetbrains.annotations.NotNull;
import org.utn.application.UserManager;
import org.utn.domain.users.Role;

import java.util.Set;

public class CustomAccessManager implements AccessManager {
    UserManager userManager;

    public CustomAccessManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public void manage(@NotNull Handler handler, @NotNull Context ctx, @NotNull Set<? extends RouteRole> permittedRoles) throws UnauthorizedResponse {
        var token = ctx.header("token");

        if(token == null || token.isEmpty()) throw new UnauthorizedResponse();

    }
}
