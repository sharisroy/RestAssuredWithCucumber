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

        // Skip auto-login for @invalid_login
        if (scenario.getSourceTagNames().contains("@invalid_login")) {
            System.out.println("⏭ Skipping auto-login for @invalid_login scenario");
            return;
        }

        // ✅ Only login ONCE per smoke test run
        if (scenario.getSourceTagNames().contains("@smoke")) {
            if (GlobalState.getAuthToken() == null) {
                System.out.println("🔑 Logging in once for all @smoke scenarios...");
                performLoginAndStoreToken();
            } else {
                System.out.println("✅ Reusing existing token for @smoke scenario.");
            }
            // Put the token into ScenarioContext for convenience
            getScenarioContext().set("authToken", GlobalState.getAuthToken());
        }
    }

    private void performLoginAndStoreToken() {
        // Create login payload from config
        LoginRequest loginPayload = new LoginRequest(
                ConfigManager.getProperty("login.email"),
                ConfigManager.getProperty("login.password")
        );

        ApiClient apiClient = new ApiClient();
        Response loginResponse = apiClient
                .setBaseUrl(ConfigManager.getProperty("base.url"))
                .setBody(loginPayload)
                .post("auth/login");

        loginResponse.then().statusCode(200);

        String token = loginResponse.jsonPath().getString("token");
        String userId = loginResponse.jsonPath().getString("userId");

        if (token == null) {
            throw new RuntimeException("❌ ERROR: Token not received!");
        }

        // ✅ Store globally
        GlobalState.setAuthToken(token);
        GlobalState.setUserId(userId);

        System.out.println("✅ Token saved globally: " + token);
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
