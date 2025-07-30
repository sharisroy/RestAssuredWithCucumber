package utilities;

import java.util.Map;

public class GlobalState {
    private static String authToken;
    private static String userId;
    private static String firstProductId;
    private static Map<String, Object> firstProduct;

    public static String getAuthToken() {
        return authToken;
    }

    public static void setAuthToken(String token) {
        authToken = token;
    }

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String id) {
        userId = id;
    }

    // ✅ product storage
    public static String getFirstProductId() {
        return firstProductId;
    }

    public static void setFirstProductId(String id) {
        firstProductId = id;
    }

    public static Map<String, Object> getFirstProduct() {
        return firstProduct;
    }

    public static void setFirstProduct(Map<String, Object> product) {
        firstProduct = product;
    }
}
