package socketGameMessage.events;

import events.Event;
import socketGameMessage.SocketGameMessage;

public class StartEvent extends SocketEvent {
    public StartEvent(int clientId) {
        super(clientId);
    }

    public StartEvent(String rawString) {
        super(rawString);
    }

    @Override
    public String toString() {
        return SocketGameMessage.types.startEvent.ordinal() + delimiter + super.toString();
    }
}
