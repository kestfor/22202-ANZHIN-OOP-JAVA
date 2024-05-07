package socketGameMessage.events;

import events.Event;

public class RestartEvent extends Event {
    private final int clientId;

    public RestartEvent(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }
}
