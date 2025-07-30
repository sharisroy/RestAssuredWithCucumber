package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import payloads.LoginRequest;
import utilities.ConfigManager;
import utilities.Hooks;
import utilities.ResponseHelper;

import java.util.Objects;

public class LoginSteps {

    @Given("I want send {string} and {string}")
    public void i_want_send_email_and_password(String email, String password) {
        LoginRequest loginPayload;
        if ("<email>".equalsIgnoreCase(email) && "<password>".equalsIgnoreCase(password)) {
            loginPayload = new LoginRequest(
                    ConfigManager.getProperty("login.email"),
                    ConfigManager.getProperty("login.password")
            );
        } else {
            loginPayload = new LoginRequest(email, password);
        }
        Hooks.getScenarioContext().set("requestPayload", loginPayload);
    }

    @And("I want to validate the login response {string}")
    public void i_want_to_validate_the_login_response(String expectedMessage) {
        Response response = (Response) Hooks.getScenarioContext().get("response");
        String actualMessage = response.jsonPath().getString("message");

        if (Objects.equals(actualMessage, "Login Successfully")) {
            ResponseHelper.storeAuthDetails(response);
        }

        if (!Objects.equals(actualMessage, expectedMessage)) {
            throw new AssertionError("❌ Expected message: " + expectedMessage +
                    " but got: " + actualMessage);
        }

        System.out.println("✅ Message Verified: " + expectedMessage);
    }
}
