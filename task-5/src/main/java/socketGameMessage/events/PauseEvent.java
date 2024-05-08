package socketGameMessage.events;

import events.Event;
import socketGameMessage.SocketGameMessage;

public class PauseEvent extends SocketEvent {

    public PauseEvent(int clientId) {
        super(clientId);
    }

    public PauseEvent(String rawString) {
        super(rawString);
    }

    @Override
    public String toString() {
        return SocketGameMessage.types.pauseEvent.ordinal() + delimiter + super.toString();
    }

}
