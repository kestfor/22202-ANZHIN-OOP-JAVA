package factory.ConfigReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ConfigReader {
    private String path = "/config.json";
    private HashMap<String, Integer> data;
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);

    public enum Settings {
        storageBodySize,
        storageMotorSize,
        storageAccessorySize,
        storageAutoSize,
        accessorySuppliers,
        workers,
        dealers,
        logSale
    }
    public ConfigReader() {
        data = new HashMap<>();
        loadConfig(path);
    }

    public Integer get(Settings name) {
        return data.getOrDefault(name.name(), null);
    }

    private void loadConfig(String src) {
        InputStream inputStream = ConfigReader.class.getResourceAsStream(src);
        if (inputStream == null) {
            return;
        }
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(new InputStreamReader(inputStream));
        } catch (IOException e) {
            logger.error("cant read config file");
            return;
        } catch (ParseException e) {
            logger.error("cant parse config file");
            return;
        }
        for (Object key : jsonObject.keySet()) {
            try {
                data.put(key.toString(), Integer.parseInt(jsonObject.get(key).toString()));
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage());
            }
        }
    }
}
