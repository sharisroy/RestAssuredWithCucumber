package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import utilities.GlobalState;
import utilities.Hooks;
import utilities.ResponseHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddToCartSteps {

    @Given("I prepare the add to cart payload")
    public void i_prepare_the_add_to_cart_payload() {
        // ✅ Get product info stored earlier by ProductSteps
        Map<String, Object> firstProduct = GlobalState.getFirstProduct();
        String userId = GlobalState.getUserId();

        if (userId == null) {
            throw new RuntimeException("❌ No userId found! Did you run the login API ?");
        }

        if (firstProduct == null) {
            throw new RuntimeException("❌ No product found! Did you run the product API ?");
        }

        // ✅ Create payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("_id", userId);
        payload.put("product", firstProduct);

        System.out.println("payload: " + payload);
//
        // ✅ Store payload in ScenarioContext for CommonSteps
        Hooks.getScenarioContext().set("requestPayload", payload);
    }

    @And("I want to validate the add to cart response {string}")
    public void iWantToValidateTheAddToCartResponse(String expectedMessage) {
        Response response = (Response) Hooks.getScenarioContext().get("response");
        String actualMessage = response.jsonPath().getString("message");

        if (Objects.equals(actualMessage, expectedMessage)) {
            ResponseHelper.storeFirstProductInfo(response);
        }

        if (!Objects.equals(actualMessage, expectedMessage)) {
            throw new AssertionError("❌ Expected message: " + expectedMessage +
                    " but got: " + actualMessage);
        }

        System.out.println("✅ Message Verified: " + expectedMessage);
    }
}
