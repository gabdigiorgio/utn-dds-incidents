package org.utn.infrastructure;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.utn.domain.incident.InventoryService;
import org.utn.utils.exceptions.validator.InvalidCatalogCodeException;

import java.io.Console;
import java.io.IOException;

public class OkInventoryService implements InventoryService {

    private final OkHttpClient client;
    private final String baseUrl;

    public OkInventoryService(OkHttpClient client, String baseUrl){
        this.client = client;
        this.baseUrl = baseUrl;
    }
    
    @Override
    public void validateCatalogCode(String catalogCode) throws IOException {
        var url = baseUrl + catalogCode;
        var request = buildRequest(url);
        var response = execute(request);
        var isNotSuccessful = !response.isSuccessful();

        if (isNotSuccessful) {
            throw new InvalidCatalogCodeException(catalogCode);
        }
    }

    @Override
    public String getInaccessibleAccessibilityFeatures(Integer limit, String line, String station) throws IOException {
        var baseUrl = "http://localhost:8081/api/accessibilityFeatures/";

        var urlBuilder = new StringBuilder(baseUrl + "?limit=" + limit + "&inaccessible=true");
        if (line != null) {
            urlBuilder.append("&line=").append(line);
        }
        if (station != null) {
            urlBuilder.append("&station=").append(station);
        }
        var url = urlBuilder.toString();

        var request = buildRequest(url);

        var response = execute(request);

        try (ResponseBody responseBody = response.body()) {
            return responseBody.string();
        }
    }

    private Response execute(Request request) throws IOException {
        return client.newCall(request).execute();
    }

    private Request buildRequest(String url) {
        return new Request.Builder()
                .url(url)
                .build();
    }
}
