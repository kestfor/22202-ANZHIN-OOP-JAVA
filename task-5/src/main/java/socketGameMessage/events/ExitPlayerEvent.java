package socketGameMessage.events;

import events.Event;

public class ExitPlayerEvent extends Event {
    private final int clientId;

    public ExitPlayerEvent(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }
}
