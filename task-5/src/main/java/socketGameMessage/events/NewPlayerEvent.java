package socketGameMessage.events;

import events.Event;
import socketGameMessage.SocketGameMessage;

public class NewPlayerEvent extends SocketEvent {

    public NewPlayerEvent(int clientId) {
        super(clientId);
    }

    public NewPlayerEvent(String rawString) {
        super(rawString);
    }

    @Override
    public String toString() {
        return SocketGameMessage.types.newPlayerEvent.ordinal() + delimiter + super.toString();
    }

}
