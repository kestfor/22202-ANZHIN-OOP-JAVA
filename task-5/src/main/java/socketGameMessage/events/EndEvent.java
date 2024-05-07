package socketGameMessage.events;

import events.Event;

public class EndEvent extends Event {
    private final int clientId;

    public EndEvent(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }
}
