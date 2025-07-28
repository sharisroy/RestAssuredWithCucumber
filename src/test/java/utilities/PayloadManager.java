package utilities;

import payloads.LoginRequest;

public class PayloadManager {

    public static LoginRequest loginPayload(String email, String password) {
        return new LoginRequest(email, password);
    }
}
