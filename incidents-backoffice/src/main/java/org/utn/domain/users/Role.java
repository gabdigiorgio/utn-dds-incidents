package org.utn.domain.users;

import io.javalin.security.RouteRole;

enum Role implements RouteRole { ANYONE, USER, OPERATOR }
