package org.utn.domain.users;

import io.javalin.security.RouteRole;

public enum Role implements RouteRole { ANYONE, USER, OPERATOR }
