package safro.deep.in.the.night.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DitnConfig {
    private static final HashMap<String, Boolean> OPTIONS = new HashMap<>();

    public static boolean getValue(String key) {
        if (!OPTIONS.containsKey(key)) {
            System.out.println(key);
        }
        return OPTIONS.getOrDefault(key, null);
    }

    public static void init() {
        OPTIONS.put("allow_scares", true);
    }

    public static void loadConfig() {
        JsonObject json = Config.getJsonObject(Config.readFile(new File("config/ditn/config.json5")));
        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                OPTIONS.put(entry.getKey(), entry.getValue().getAsBoolean());
        }
    }

    public static void generateConfigs(boolean overwrite) {
        StringBuilder config = new StringBuilder("{\n");
        int i = 0;
        for (String key : OPTIONS.keySet()) {
            config.append("  \"").append(key).append("\": ").append(OPTIONS.get(key));
            ++i;
            if (i < OPTIONS.size()) {
                config.append(",");
            }
            config.append("\n");
        }
        config.append("}");
        Config.createFile("config/ditn/config.json5", config.toString(), overwrite);
    }
}
