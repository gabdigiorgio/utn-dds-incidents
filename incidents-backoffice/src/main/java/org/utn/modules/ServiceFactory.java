package org.utn.modules;

import okhttp3.OkHttpClient;
import org.utn.infrastructure.bcrypt_password_hasher.BCryptPasswordHasher;
import org.utn.infrastructure.ok_inventory_service.OkInventoryService;

public class ServiceFactory {

    public static OkInventoryService createInventoryService() {
        OkHttpClient client = new OkHttpClient();
        String baseUrl = System.getenv("INVENTORY_SERVICE_URL");

        if (baseUrl == null || baseUrl.isEmpty()) {
            baseUrl = "http://localhost:8081/api";
        }

        return new OkInventoryService(client, baseUrl);
    }
    
    public static BCryptPasswordHasher createPasswordHasher() {
        return new BCryptPasswordHasher();
    }
}
