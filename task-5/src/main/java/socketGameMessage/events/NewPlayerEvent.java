package socketGameMessage.events;

import events.Event;

public class NewPlayerEvent extends Event {
    private final int clientId;

    public NewPlayerEvent(int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }
}
