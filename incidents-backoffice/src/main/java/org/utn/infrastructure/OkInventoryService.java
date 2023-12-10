package org.utn.infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.utn.domain.accessibility_feature.AccessibilityFeature;
import org.utn.domain.accessibility_feature.AccessibilityFeatures;
import org.utn.domain.accessibility_feature.Line;
import org.utn.domain.accessibility_feature.Station;
import org.utn.domain.incident.InventoryService;
import org.utn.infrastructure.responses.AccessibilityFeaturesInventoryResponse;
import org.utn.infrastructure.responses.LineInventoryResponse;
import org.utn.infrastructure.responses.StationInventoryResponse;
import org.utn.utils.exceptions.validator.InvalidCatalogCodeException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class OkInventoryService implements InventoryService {

    private final OkHttpClient client;
    private final String baseUrl;

    public OkInventoryService(OkHttpClient client, String baseUrl) {
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

    public void setAccessibilityFeatureStatus(String catalogCode, String status) throws IOException {
        String url = baseUrl + "/accessibility-features/" + catalogCode;

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String jsonBody = "{\"status\": \"" + status + "\"}";

        RequestBody requestBody = RequestBody.create(JSON, jsonBody);

        Request request = new Request.Builder()
                .url(url)
                .put(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Error response: " + response.body().string());
                throw new InvalidCatalogCodeException(catalogCode);
            }
        }
    }

    @Override
    public AccessibilityFeatures getAccessibilityFeatures(Integer limit, String status, String line, String station,
                                                          Integer page, Integer pageSize) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl + "/accessibility-features/").newBuilder();

        if (limit != null) {
            urlBuilder.addQueryParameter("limit", limit.toString());
        }
        if (page != null) {
            urlBuilder.addQueryParameter("page", page.toString());
        }
        if (pageSize != null) {
            urlBuilder.addQueryParameter("pageSize", pageSize.toString());
        }
        if (status != null) {
            urlBuilder.addQueryParameter("status", status.toString());
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
            ObjectMapper objectMapper = new ObjectMapper();

            AccessibilityFeaturesInventoryResponse inventoryResponse = objectMapper.readValue(responseBody.string(), new TypeReference<>() {
            });

            return mapToAccessibilityFeatures(inventoryResponse);
        }
    }

    private AccessibilityFeatures mapToAccessibilityFeatures(AccessibilityFeaturesInventoryResponse inventoryResponse) {
        List<AccessibilityFeature> items = inventoryResponse.getItems().stream()
                        .map(feature -> {
                            AccessibilityFeature responseItem = new AccessibilityFeature();
                            responseItem.setCatalogCode(feature.getCatalogCode());
                            responseItem.setType(feature.getType());
                            responseItem.setStatus(feature.getStatus());
                            responseItem.setStation(feature.getStation());
                            responseItem.setLine(feature.getLine());
                            return responseItem;
                        })
                        .collect(Collectors.toList());
        return new AccessibilityFeatures(items, inventoryResponse.getTotalCount());
    }


    @Override
    public List<Line> getLines() throws IOException {
        var url = baseUrl + "/lines";

        var request = buildRequest(url);

        var response = execute(request);

        try (ResponseBody responseBody = response.body()) {

            ObjectMapper objectMapper = new ObjectMapper();

            List<LineInventoryResponse> inventoryResponseList = objectMapper.readValue(responseBody.string(), new TypeReference<>() {
            });

            return inventoryResponseList.stream().map(this::mapToLine).collect(Collectors.toList());
        }
    }

    private Line mapToLine(LineInventoryResponse inventoryResponse) {
        Line line = new Line();
        line.setId(inventoryResponse.getId());
        line.setName(inventoryResponse.getName());
        return line;
    }

    @Override
    public List<Station> getStationsFromLine(String lineId) throws IOException {
        var url = baseUrl + "/lines/" + lineId + "/stations";

        var request = buildRequest(url);

        var response = execute(request);

        try (ResponseBody responseBody = response.body()) {

            ObjectMapper objectMapper = new ObjectMapper();

            List<StationInventoryResponse> inventoryResponseList = objectMapper.readValue(responseBody.string(), new TypeReference<>() {
            });

            return inventoryResponseList.stream().map(this::mapToStation).collect(Collectors.toList());
        }
    }

    private Station mapToStation(StationInventoryResponse inventoryResponse) {
        Station station = new Station();
        station.setId(inventoryResponse.getId());
        station.setName(inventoryResponse.getName());
        return station;
    }

    private Response execute(Request request) throws IOException {
        return client.newCall(request).execute();
    }

    private Request buildRequest(String url) {
        return new Request.Builder().url(url).build();
    }
}
