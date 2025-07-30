package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utilities.ApiClient;
import utilities.ConfigManager;
import utilities.Hooks;

public class CommonSteps {
    private ApiClient getApiClient() {
        ApiClient client = (ApiClient) Hooks.getScenarioContext().get("apiClient");
        if (client == null) {
            client = new ApiClient();
            Hooks.getScenarioContext().set("apiClient", client);
        }
        return client;
    }

    private void applyAuthorizationHeader(ApiClient apiClient) {
        Boolean skipAuth = (Boolean) Hooks.getScenarioContext().get("skipAuthHeader");
        if (Boolean.FALSE.equals(skipAuth) || skipAuth == null) {
            if (!apiClient.hasHeader("Authorization")) {
                String token = (String) Hooks.getScenarioContext().get("authToken");
                if (token != null) {
                    apiClient.addHeader("Authorization", token);
                }
            }
        }
    }

    @When("I want to send a POST request to {string}")
    public void i_want_to_send_a_post_request_to(String endpoint) {
        ApiClient apiClient = getApiClient();
        apiClient.setBaseUrl(ConfigManager.getProperty("base.url"));

        applyAuthorizationHeader(apiClient);

        Object requestPayload = Hooks.getScenarioContext().get("requestPayload");
        if (requestPayload != null) {
            apiClient.setBody(requestPayload);
        }

        Response response = apiClient.post(endpoint);
        Hooks.getScenarioContext().set("response", response);
    }

    @Then("I should receive a {int} status code")
    public void i_should_receive_a_status_code(int statusCode) {
        Response response = (Response) Hooks.getScenarioContext().get("response");
        response.then().statusCode(statusCode);
        System.out.println("✅ Status Code Verified: " + statusCode);
    }


    @And("I set an invalid token in the header")
    public void i_set_an_invalid_token_in_the_header() {
        ApiClient apiClient = getApiClient();
        apiClient.addHeader("Authorization", "Bearer invalid_token_123");
        Hooks.getScenarioContext().set("skipAuthHeader", true);
    }

    @And("I set an blank token in the header")
    public void i_set_an_blank_token_in_the_header() {
        ApiClient apiClient = getApiClient();
        apiClient.removeHeader("Authorization");
        Hooks.getScenarioContext().set("skipAuthHeader", true);
    }
}
