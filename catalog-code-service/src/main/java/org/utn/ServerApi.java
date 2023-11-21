package org.utn;

import io.javalin.Javalin;

public class ServerApi {
    public static void main(String[] args) {

        Integer port = Integer.parseInt(System.getProperty("port", "8080"));
        Javalin server = Javalin.create().start(port);
    }
}