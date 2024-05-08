package socketGameMessage.events;

import events.Event;
import socketGameMessage.SocketGameMessage;

public class RestartEvent extends SocketEvent {

    public RestartEvent(int clientId) {
        super(clientId);
    }

    public RestartEvent(String rawString) {
        super(rawString);
    }

    @Override
    public String toString() {
        return SocketGameMessage.types.restartEvent.ordinal() + delimiter + super.toString();
    }

}
