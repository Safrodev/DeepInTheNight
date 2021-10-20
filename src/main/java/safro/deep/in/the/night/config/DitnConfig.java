package safro.deep.in.the.night.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DitnConfig {
    private static final HashMap<String, Boolean> BOOLEAN_OPTIONS = new HashMap<>();
    private static final HashMap<String, Integer> INTEGER_OPTIONS = new HashMap<>();

    public static boolean getBooleanValue(String key) {
        if (!BOOLEAN_OPTIONS.containsKey(key)) {
            System.out.println(key);
        }
        return BOOLEAN_OPTIONS.getOrDefault(key, null);
    }

    public static int getIntValue(String key) {
        if (!INTEGER_OPTIONS.containsKey(key)) {
            System.out.println(key);
        }
        return INTEGER_OPTIONS.getOrDefault(key, null);
    }

    public static void init() {
        BOOLEAN_OPTIONS.put("allow_scares", true);

        INTEGER_OPTIONS.put("scare_chance", 10000);
        INTEGER_OPTIONS.put("headless_horseman_chance", 8000);
    }

    public static void loadConfig() {
        JsonObject json;
        json = Config.getJsonObject(Config.readFile(new File("config/ditn/true_false_config.json5")));
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                BOOLEAN_OPTIONS.put(entry.getKey(), entry.getValue().getAsBoolean());
        }

        json = Config.getJsonObject(Config.readFile(new File("config/ditn/number_config.json5")));
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
            INTEGER_OPTIONS.put(entry.getKey(), entry.getValue().getAsInt());
        }
    }

    public static void generateConfigs(boolean overwrite) {
        StringBuilder config = new StringBuilder("{\n");
        int i = 0;
        for (String key : BOOLEAN_OPTIONS.keySet()) {
            config.append("  \"").append(key).append("\": ").append(BOOLEAN_OPTIONS.get(key));
            ++i;
            if (i < BOOLEAN_OPTIONS.size()) {
                config.append(",");
            }
            config.append("\n");
        }
        config.append("}");
        Config.createFile("config/ditn/true_false_config.json5", config.toString(), overwrite);

        config = new StringBuilder("{\n");
        i = 0;
        for (String item : INTEGER_OPTIONS.keySet()) {
            config.append("  \"").append(item).append("\": ").append(INTEGER_OPTIONS.get(item));
            ++i;
            if (i < INTEGER_OPTIONS.size()) {
                config.append(",");
            }
            config.append("\n");
        }
        config.append("}");
        Config.createFile("config/ditn/number_config.json5", config.toString(), overwrite);
    }
}
