package org.utn.modules;

import okhttp3.OkHttpClient;
import org.utn.infrastructure.OkInventoryService;

public class ServiceFactory {

    public static OkInventoryService createInventoryService() {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.example.com/endpoint"; //TODO: CAMBIAR
        return new OkInventoryService(client, url);
    }
}
