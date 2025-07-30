package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import utilities.ApiClient;
import utilities.ConfigManager;
import utilities.Hooks;

import java.util.HashMap;
import java.util.Map;

public class AddToCartSteps {

    @SuppressWarnings("unchecked")
    @Given("I want to send valid add to card payload")
    public void i_want_to_send_valid_add_to_card_payload() {
        Map<String, Object> product = (Map<String, Object>) Hooks.getScenarioContext().get("firstProduct");
        String userId = (String) Hooks.getScenarioContext().get("userId");

        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", userId);
        payload.put("product", product);

        Hooks.getScenarioContext().set("cardPayload", payload);
    }

    @When("I want to send a POST request add to card to {string}")
    public void i_want_to_send_a_post_request_to(String endpoint) {
        ApiClient apiClient = new ApiClient();
        apiClient.setBaseUrl(ConfigManager.getProperty("base.url"));


        Object requestPayload = Hooks.getScenarioContext().get("requestPayload");
        if (requestPayload != null) {
            apiClient.setBody(requestPayload);
        }

        Response response = apiClient.post(endpoint);
        Hooks.getScenarioContext().set("response", response);
    }

//    @And("I want to validate the add to cart response {string}")
//    public void iWantToValidateTheAddToCartResponse() {
//
//    }
}
