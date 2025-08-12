package stepDefinitions;

import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utilities.ApiClient;
import utilities.ConfigManager;
import utilities.GlobalState;
import utilities.Hooks;

public class GetCartProductSteps {
    private ApiClient getApiClient() {
        ApiClient client = (ApiClient) Hooks.getScenarioContext().get("apiClient");
        if (client == null) {
            client = new ApiClient();
            Hooks.getScenarioContext().set("apiClient", client);
        }
        return client;
    }

    @When("I want to send a GET request to {string}")
    public void i_want_to_send_a_get_request_to(String endpoint) {
        ApiClient apiClient = getApiClient();
        apiClient.setBaseUrl(ConfigManager.getProperty("base.url"));
        applyAuthorizationHeader(apiClient);

        String userId = GlobalState.getUserId();
//         Send the GET request
        Response response = apiClient.get(endpoint + userId);

//         Optionally store the response
         Hooks.getScenarioContext().set("response", response);
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

}
