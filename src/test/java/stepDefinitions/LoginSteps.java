package stepDefinitions;

import io.cucumber.java.en.Given;
import payloads.LoginRequest;
import utilities.ConfigManager;
import utilities.Hooks;

public class LoginSteps {
    private LoginRequest loginPayload;

    @Given("I want send {string} and {string}")
    public void i_want_send_email_and_password(String email, String password) {
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
}
