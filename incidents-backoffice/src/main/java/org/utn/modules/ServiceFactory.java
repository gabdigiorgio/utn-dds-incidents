package org.utn.modules;

import okhttp3.OkHttpClient;
import org.utn.infrastructure.OkInventoryService;

public class ServiceFactory {

    public static OkInventoryService createInventoryService() {
        OkHttpClient client = new OkHttpClient();
        String baseUrl = "http://localhost:8081/api/accessibilityFeature/"; //TODO: CAMBIAR CON DEPLOY
        return new OkInventoryService(client, baseUrl);
    }
}
