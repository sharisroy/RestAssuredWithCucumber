package utilities;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import payloads.LoginRequest;

public class Hooks {
    private static final ThreadLocal<ScenarioContext> scenarioContextThreadLocal = new ThreadLocal<>();

    @Before
    public void setup(Scenario scenario) {
        System.out.println("Starting scenario: " + scenario.getName());
        scenarioContextThreadLocal.set(new ScenarioContext());

        //  Skip auto-login for scenarios tagged with @login
//        if (scenario.getSourceTagNames().contains("@login")) {
//            System.out.println("⏭ Skipping auto-login for scenario tagged @login");
//            return;
//        }


        // Create login payload from config
        LoginRequest loginPayload = new LoginRequest(
                ConfigManager.getProperty("login.email"),
                ConfigManager.getProperty("login.password")
        );

        // login if scenario lost session
        ApiClient apiClient = new ApiClient();
        Response loginResponse = apiClient
                .setBaseUrl(ConfigManager.getProperty("base.url"))
                .setBody(loginPayload)
                .post("auth/login");

        loginResponse.then().statusCode(200);
        String token = loginResponse.jsonPath().getString("token");
        String userId = loginResponse.jsonPath().getString("userId");

        if(token == null && userId == null) {
            System.out.println(" ERROR: No token found");
        } else {
            System.out.println(" Token stored in ScenarioContext: " + token);
        }
        // Store in ScenarioContext for later use
        getScenarioContext().set("authToken", token);
        getScenarioContext().set("userId", userId);

    }
    @After
    public void teardown() {
        System.out.println(" Cleaning up after scenario...");
        scenarioContextThreadLocal.remove();
    }

    public static ScenarioContext getScenarioContext() {
        return scenarioContextThreadLocal.get();
    }
}
