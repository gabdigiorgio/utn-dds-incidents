package org.utn;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.javalin.Javalin;
import org.utn.modules.ManagerFactory;
import org.utn.presentation.url_mappings.AccessibilityResource;

public class ServerApi {
    public static void main(String[] args) {

        var accessibilityFeatureManager = ManagerFactory.createAccessibilityFeatureManager();

        Integer port = Integer.parseInt(System.getProperty("port", "8081")); //TODO: CAMBIAR CON DEPLOY
        Javalin server = Javalin.create().start(port);

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
}