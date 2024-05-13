package socketGameMessage;

import socketGameMessage.events.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class SocketGameMessage {
    private String message;

    protected static HashMap<types, Class> map = new HashMap<types, Class>() {{
        put(types.directionEvent, DirectionEvent.class);
        put(types.pauseEvent, PauseEvent.class);
        put(types.startEvent, StartEvent.class);
        put(types.endEvent, EndEvent.class);
        put(types.restartEvent, RestartEvent.class);
        put(types.newAppleEvent, NewAppleEvent.class);
        put(types.newPlayerEvent, NewPlayerEvent.class);
        put(types.exitPlayerEvent, ExitPlayerEvent.class);
        put(types.tickUpdate, TickUpdate.class);
    }};

    public enum types {
        directionEvent,
        pauseEvent,
        startEvent,
        endEvent,
        restartEvent,
        newAppleEvent,
        newPlayerEvent,
        exitPlayerEvent,
        tickUpdate
    }

    @Override
    public String toString() {
        return message;
    }

    public SocketEvent getEvent() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String[] tokens = message.split(String.valueOf(SocketEvent.delimiter));
        types type = types.values()[Integer.parseInt(tokens[0])];
        StringBuilder rawString= new StringBuilder();
        for (int i = 1; i < tokens.length; i++) {
            rawString.append(SocketEvent.delimiter).append(tokens[i]);
        }
        rawString.deleteCharAt(0);
        Class<?> cl = map.get(type);
        Constructor<?> cons = cl.getConstructor(String.class);
        return (SocketEvent) cons.newInstance(rawString.toString());
    }

    public SocketGameMessage(SocketEvent event) {
        this.message = event.toString();
    }


    public SocketGameMessage(String message) {
        this.message = message;
    }
}
