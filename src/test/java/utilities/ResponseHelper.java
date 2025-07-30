package utilities;

import io.restassured.response.Response;

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
        String productId = response.jsonPath().getString("data[0]._id");
        String productName = response.jsonPath().getString("data[0].productName");
        String productCategory = response.jsonPath().getString("data[0].productCategory");

        if (productId != null) {
            Hooks.getScenarioContext().set("firstProductId", productId);
            System.out.println("🆔 Product ID stored: " + productId);
        }

        if (productName != null) {
            Hooks.getScenarioContext().set("firstProductName", productName);
            System.out.println("📦 Product Name stored: " + productName);
        }

        if (productCategory != null) {
            Hooks.getScenarioContext().set("firstProductCategory", productCategory);
            System.out.println("🗂️ Product Category stored: " + productCategory);
        }


//        int totalProducts = response.jsonPath().getList("data").size();
//        System.out.println("📦 Total products returned: " + totalProducts);
//
//        if (totalProducts == 0) {
//            throw new AssertionError("❌ Expected non-empty product list but got 0.");
//        }
//
//        // Store first product details for next test
//        String firstProductId = response.jsonPath().getString("data[0]._id");
//        String firstProductName = response.jsonPath().getString("data[0].productName");
//
//        Hooks.getScenarioContext().set("firstProductId", firstProductId);
//        Hooks.getScenarioContext().set("firstProductName", firstProductName);
//
//        System.out.println("✅ Stored First Product: ID = " + firstProductId + ", Name = " + firstProductName);

    }
}
