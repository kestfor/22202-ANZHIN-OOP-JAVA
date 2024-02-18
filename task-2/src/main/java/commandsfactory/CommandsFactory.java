package commandsfactory;

import commands.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import org.json.*;

public class CommandsFactory {

    HashMap<String, Command> _factory;
    private static final int buffSize = 1000;
    private final String configFile = "fabricConfig.json";

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
            throw new IllegalAccessException("no commands with name '" + name + "' was found");
        }
    }

    private void loadCommands(String src) {
        Reader reader = null;
        StringBuilder jsonString;
        InputStream inputStream = getClass().getResourceAsStream(src);
        if (inputStream == null) {
            return;
        }
        try {
            reader = new InputStreamReader(inputStream);
            jsonString = new StringBuilder();
            char[] buffer = new char[buffSize];
            while (reader.read(buffer, 0, buffSize) != -1) {
                jsonString.append(String.valueOf(buffer));
            }
        } catch (IOException e) {
            throw new RuntimeException("can't read file");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        JSONObject jsonObject = new JSONObject(jsonString.toString());
        for (String key : jsonObject.keySet()) {
            try {
                _factory.put(key, createCommand(jsonObject.getString(key)));
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }
        }
    }
}
