package org.utn.infrastructure;

import okhttp3.*;
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
        var url = baseUrl + "/accessibility-features/" + catalogCode;
        var request = buildRequest(url);
        var response = execute(request);
        var isNotSuccessful = !response.isSuccessful();

        if (isNotSuccessful) {
            throw new InvalidCatalogCodeException(catalogCode);
        }
    }

    @Override
    public String getInaccessibleAccessibilityFeatures(Integer limit, String line, String station) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + "/accessibility-features/")
                .newBuilder()
                .addQueryParameter("status", "inaccessible");

        if (limit != null) {
            urlBuilder.addQueryParameter("limit", limit.toString());
        }
        if (line != null) {
            urlBuilder.addQueryParameter("line", line);
        }
        if (station != null) {
            urlBuilder.addQueryParameter("station", station);
        }

        var url = urlBuilder.build().toString();

        var request = buildRequest(url);

        var response = execute(request);

        try (ResponseBody responseBody = response.body()) {
            return responseBody.string();
        }
    }

    @Override
    public String getLines() throws IOException {
        var url = baseUrl + "/lines";

        var request = buildRequest(url);

        var response = execute(request);

        try (ResponseBody responseBody = response.body()) {
            return responseBody.string();
        }
    }

    @Override
    public String getStationsFromLine(String lineId) throws IOException {
        var url = baseUrl + "/lines/" + lineId + "/stations";

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
