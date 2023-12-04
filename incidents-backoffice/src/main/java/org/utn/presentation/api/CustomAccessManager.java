package org.utn.presentation.api;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.security.AccessManager;
import io.javalin.security.RouteRole;
import org.jetbrains.annotations.NotNull;
import org.utn.domain.users.Role;
import org.utn.domain.users.User;
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
        User user = null;

        var cookie = ctx.cookie("auth");
        var token = ctx.header("token");
        var userAgent = ctx.header("User-Agent");

        boolean isBrowserRequest = userAgent != null && userAgent.matches(".*(Mozilla|Chrome|Safari|Edge|Opera).*");

        if (cookie != null && !cookie.isEmpty()) {
            user = usersRepository.getByToken(cookie);
        }

        if (user == null && token != null && !token.isEmpty()) {
            user = usersRepository.getByToken(token);
        }

        if (user == null) {
            if (isBrowserRequest && (cookie == null || cookie.isEmpty())) {
                ctx.render("login.hbs");
                return;
            }
            throw new UnauthorizedResponse();
        }

        if (!permittedRoles.contains(user.getRole())) {
            if (isBrowserRequest) {
                ctx.render("unauthorized.hbs");
                return;
            }
            throw new UnauthorizedResponse();
        }

        handler.handle(ctx);
    }

}