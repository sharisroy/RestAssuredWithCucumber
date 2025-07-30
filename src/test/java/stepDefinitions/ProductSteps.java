package stepDefinitions;

import io.cucumber.java.en.Given;
import utilities.Hooks;

public class ProductSteps {
    @Given("I want to send valid information")
    public void i_want_send_valid_information() {
        Hooks.getScenarioContext().set("requestPayload", null);
    }
}
