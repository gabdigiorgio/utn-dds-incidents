package org.utn.infrastructure;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.utn.domain.incident.InventoryService;
import org.utn.utils.exceptions.validator.InvalidCatalogCodeException;

import java.io.IOException;

public class OkInventoryService implements InventoryService {

    private final OkHttpClient client;
    private final String url;

    public OkInventoryService(OkHttpClient client, String url){
        this.client = client;
        this.url = url;
    }
    
    @Override
    public void validateCatalogCode(String catalogCode) throws IOException {
        var request = buildRequest();
        var response = execute(request);
        var isNotSuccessful = !response.isSuccessful();

        if (isNotSuccessful) {
            throw new InvalidCatalogCodeException(catalogCode);
        }
    }

    private Response execute(Request request) throws IOException {
        return client.newCall(request).execute();
    }

    private Request buildRequest() {
        return new Request.Builder()
                .url(url)
                .build();
    }
}
