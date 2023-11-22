package org.utn;

import io.javalin.Javalin;
import org.utn.modules.ManagerFactory;

public class ServerApi {
    public static void main(String[] args) {

        var accessibilityFeatureManager = ManagerFactory.createAccessibilityFeatureManager();

        Integer port = Integer.parseInt(System.getProperty("port", "8080"));
        Javalin server = Javalin.create().start(port);
    }
}