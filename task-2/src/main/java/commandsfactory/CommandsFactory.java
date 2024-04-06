package commandsfactory;

import commands.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandsFactory {

    HashMap<String, Command> _factory;

    public static final Logger logger = LoggerFactory.getLogger(CommandsFactory.class);

    private final String configFile = "/fabricConfig.json";

    public CommandsFactory() {
        _factory = new HashMap<>();
        this.loadCommands(configFile);
    }

    private Command createCommand(String name) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return (Command) Class.forName(name).getDeclaredConstructor().newInstance();
    }

    public Command getCommand(String name) throws IllegalAccessException {
        if (_factory.containsKey(name)) {
            return _factory.get(name);
        } else {
            throw new IllegalAccessException("no command with name '" + name + "' was found");
        }
    }

    private void loadCommands(String src) {
        InputStream inputStream = CommandsFactory.class.getResourceAsStream(src);
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
                _factory.put(key.toString(), createCommand(jsonObject.get(key).toString()));
            } catch (Exception e) {
                logger.error(e.getLocalizedMessage());
            }
        }
    }
}
