package org.utn.modules;

import okhttp3.OkHttpClient;
import org.utn.infrastructure.OkInventoryService;

public class ServiceFactory {

    public static OkInventoryService createInventoryService() {
        OkHttpClient client = new OkHttpClient();
        String baseUrl = System.getenv("INVENTORY_SERVICE_URL");

        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = "http://localhost:8081/api";
        }

        return new OkInventoryService(client, baseUrl);
    }
}
