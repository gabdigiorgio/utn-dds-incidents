package org.utn;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.utn.modules.ManagerFactory;
import org.utn.presentation.url_mappings.AccessibilityResource;

public class ServerApi {
    public static void main(String[] args) {

        var accessibilityFeatureManager = ManagerFactory.createAccessibilityFeatureManager();

        Integer port = Integer.parseInt(System.getProperty("port", "8081")); //TODO: CAMBIAR CON DEPLOY
        Javalin server = Javalin.create(config -> {
            config.plugins.enableCors(cors -> {
                cors.add(it -> {
                    it.anyHost();
                });
            });
        }).start(port);

        server.routes(new AccessibilityResource(accessibilityFeatureManager, createObjectMapper()));
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    private static Handler cors = new Handler() {
        @Override
        public void handle(Context ctx) throws Exception {
            ctx.header("Access-Control-Allow-Origin", "*");
            ctx.header("Access-Control-Allow-Credentials", "true");
            ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            ctx.header("Access-Control-Allow-Headers", "*");
        }
    };
}