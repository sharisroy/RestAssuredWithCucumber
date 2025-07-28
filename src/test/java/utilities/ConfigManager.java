package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static Properties properties = new Properties();
    private static String environment;

    static {
        // ✅ Default environment = stage if none is set
        environment = System.getProperty("env", "staging");
        loadProperties(environment);
    }

    private static void loadProperties(String environment) {
        try {
            String path = "src/test/resources/config/config-" + environment + ".properties";
            FileInputStream fis = new FileInputStream(path);
            properties.load(fis);
            fis.close();
            System.out.println("✅ Loaded config for environment: " + environment);
        } catch (IOException e) {
            throw new RuntimeException("❌ Failed to load properties for environment: " + environment, e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
