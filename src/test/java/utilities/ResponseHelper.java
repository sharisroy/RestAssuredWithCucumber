package utilities;

import io.restassured.response.Response;

public class ResponseHelper {

    /**
     * ✅ Extract token & userId from response and store them in ScenarioContext.
     */
    public static void storeAuthDetails(Response response) {
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
    }
}
