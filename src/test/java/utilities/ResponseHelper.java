package utilities;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.Map;

public class ResponseHelper {

    /**
       Extract token & userId from response and store them in ScenarioContext.
     */
    public static void storeAuthDetails(Response response) {
        String token = response.jsonPath().getString("token");
        String userId = response.jsonPath().getString("userId");

        if (token != null) {
            Hooks.getScenarioContext().set("authToken", token);
//            System.out.println("🔑 Token stored in ScenarioContext: " + token);
        }
        if (userId != null) {
            Hooks.getScenarioContext().set("userId", userId);
//            System.out.println("👤 userId stored in ScenarioContext: " + userId);
        }
    }



    public static void storeFirstProductInfo(Response response) {
        JsonPath jsonPath = response.jsonPath();

        // Get first product
        Map<String, Object> firstProduct = jsonPath.getMap("data[0]");

        if (firstProduct != null && !firstProduct.isEmpty()) {
            Hooks.getScenarioContext().set("firstProduct", firstProduct);
            GlobalState.setFirstProduct(firstProduct);
//            System.out.println("🧾 Full First Product stored: " + firstProduct);
        }
    }


}
