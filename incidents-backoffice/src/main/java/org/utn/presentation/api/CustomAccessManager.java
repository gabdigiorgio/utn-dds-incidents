package org.utn.presentation.api;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.security.AccessManager;
import io.javalin.security.RouteRole;
import org.jetbrains.annotations.NotNull;
import org.utn.domain.users.Role;
import org.utn.modules.RepositoryFactory;

import java.util.Set;

public class CustomAccessManager implements AccessManager {

    @Override
    public void manage(@NotNull Handler handler, @NotNull Context ctx, @NotNull Set<? extends RouteRole> permittedRoles) throws Exception {
        if (permittedRoles.contains(Role.ANYONE)) {
            handler.handle(ctx);
            return;
        }

        var usersRepository = RepositoryFactory.createUserRepository();

        String token = ctx.cookie("auth");
        if (token == null || token.isEmpty()) {
            ctx.render("login.hbs");
            return;
        }

        var user = usersRepository.getByToken(token);
        if (user == null || !permittedRoles.contains(user.getRole())) {
            ctx.render("login.hbs");
            return;
        }

        handler.handle(ctx);
    }
}
