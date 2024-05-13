package socketGameMessage.events;

import events.Event;

public class SocketEvent extends Event {
    public static String delimiter = ":";

    protected int clientId=-1;

    public SocketEvent() {}

    public SocketEvent(int clientId) {
        this.clientId = clientId;
    }

    public SocketEvent(String rawString) {
        this(Integer.parseInt(rawString));
    }

    public int getClientId() {
        return clientId;
    }

    public String toString() {
        return String.valueOf(clientId);
    }
}
