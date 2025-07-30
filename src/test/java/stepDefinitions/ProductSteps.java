package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import utilities.Hooks;
import utilities.ResponseHelper;

import java.util.Objects;

public class ProductSteps {

    @And("I want to validate the get product response {string}")
    public void i_want_to_validate_the_get_product_response(String expectedMessage) {
        Response response = (Response) Hooks.getScenarioContext().get("response");
        String actualMessage = response.jsonPath().getString("message");

        if (Objects.equals(actualMessage, "All Products fetched Successfully")) {
            ResponseHelper.storeFirstProductInfo(response);
        }

        if (!Objects.equals(actualMessage, expectedMessage)) {
            throw new AssertionError("❌ Expected message: " + expectedMessage +
                    " but got: " + actualMessage);
        }

        System.out.println("✅ Message Verified: " + expectedMessage);
    }
}
