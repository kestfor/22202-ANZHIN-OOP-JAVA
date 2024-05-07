package socketGameMessage.events;

import events.Event;

public class PauseEvent extends Event {
    private final int clientId;

    public PauseEvent(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }
}
