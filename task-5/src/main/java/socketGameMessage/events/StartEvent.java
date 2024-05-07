package socketGameMessage.events;

import events.Event;

public class StartEvent extends Event {
    private final int clientId;

    public StartEvent(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }
}
