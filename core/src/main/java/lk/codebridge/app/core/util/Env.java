package lk.codebridge.app.core.util;

import java.io.InputStream;
import java.util.Properties;

public class Env {
    private static final Properties properties = new Properties();

    static {
        try (InputStream is = Env.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (is != null) {
                properties.load(is);
            } else {
                System.err.println("ERROR: 'application.properties' not found in classpath.");
            }
        } catch (Exception e) {
            System.err.println("ERROR: Failed to load 'application.properties'");
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
