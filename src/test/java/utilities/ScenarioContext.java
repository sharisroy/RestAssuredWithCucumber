package utilities;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {
    private final Map<String, Object> contextData = new HashMap<>();

    public void set(String key, Object value) {
        contextData.put(key, value);
    }

    public Object get(String key) {
        return contextData.get(key);
    }

    public <T> T get(String key, Class<T> type) {
        return type.cast(contextData.get(key));
    }
}
