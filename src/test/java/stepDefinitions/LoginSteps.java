package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import payloads.LoginRequest;
import utilities.ApiClient;
import utilities.ConfigManager;
import utilities.Hooks;

import java.util.Objects;

public class LoginSteps {

    private final ApiClient apiClient = new ApiClient();
    private Response response;
    LoginRequest loginPayload;


    @Given("I want send {string} and {string}")
    public void i_want_send_email_and_password(String email, String password) {

        if (email.equalsIgnoreCase("<email>") && password.equalsIgnoreCase("<password>")) {
            loginPayload = new LoginRequest(
                    ConfigManager.getProperty("login.email"),
                    ConfigManager.getProperty("login.password")
            );
        } else {
            loginPayload = new LoginRequest(email, password);
        }
    }

    @When("I want to send a request to {string}")
    public void i_want_to_send_a_request_to(String endpoint) {
        response = apiClient
                .setBaseUrl(ConfigManager.getProperty("base.url"))
                .setBody(loginPayload)
                .post(endpoint);
    }

    @Then("I should receive a {int} status code")
    public void i_should_receive_a_status_code(int statusCode) {
        response.then().statusCode(statusCode);
        System.out.println("✅ Status Code Verified: " + statusCode);
    }

    @And("I want to validate the response")
    public void i_want_to_validate_the_response() {
        if (Objects.equals(response.jsonPath().getString("message"), "Login Successfully")) {
            System.out.println("✅ Response Body:\n" + response.getBody().asPrettyString());

            String token = response.jsonPath().getString("token");
            String userId = response.jsonPath().getString("userId");
            if (token != null) {
                Hooks.getScenarioContext().set("authToken", token);
                System.out.println("🔑 Token stored in ScenarioContext: " + token);
            }
            if (userId != null) {
                Hooks.getScenarioContext().set("userId", userId);
                System.out.println("👤 userId stored in ScenarioContext: " + userId);
            }
        }else {
            System.out.println("✅ Response Body:\n" + response.getBody().asPrettyString());
        }

    }
}
