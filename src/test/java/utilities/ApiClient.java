package utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class ApiClient {
    private final Map<String, String> headers = new HashMap<>();
    private Object body;
    private String baseUrl;

    public ApiClient() {
        headers.put("Content-Type", "application/json");
    }

    public ApiClient setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }
    public String getBaseUrl() {
        return baseUrl;
    }

    public ApiClient setBody(Object body) {
        this.body = body;
        return this;
    }

    public ApiClient addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public ApiClient removeHeader(String key) {
        headers.remove(key);
        return this;
    }
    public Map<String, String> getHeaders() {
        return new HashMap<>(headers); // return a copy to avoid external modification
    }
    public boolean hasHeader(String key) {
        return headers.containsKey(key);
    }

    private RequestSpecification buildRequest() {
        RequestSpecification req = RestAssured.given();

        // Apply headers
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            req.header(entry.getKey(), entry.getValue());
        }

        // Set body if available
        if (body != null) {
            req.body(body);
        }

        // Set base URL
        if (baseUrl != null) {
            req.baseUri(baseUrl);
        }

        return req;
    }

    public Response post(String endpoint) {
        return buildRequest().when().post(endpoint);
    }

    public Response get(String endpoint) {
        return buildRequest().when().get(endpoint);
    }

    public Response put(String endpoint) {
        return buildRequest().when().put(endpoint);
    }

    public Response delete(String endpoint) {
        return buildRequest().when().delete(endpoint);
    }
}
