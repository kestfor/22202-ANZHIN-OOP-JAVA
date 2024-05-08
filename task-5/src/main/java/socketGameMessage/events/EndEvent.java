package socketGameMessage.events;

import events.Event;
import socketGameMessage.SocketGameMessage;

public class EndEvent extends SocketEvent {

    public enum endType {
        draw(-1),
        lose(-2);

        private int value;
        endType(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }

    public EndEvent(int clientId) {
        super(clientId);
    }

    public EndEvent(String rawString) {
        super(rawString);
    }

    @Override
    public String toString() {
        return SocketGameMessage.types.endEvent.ordinal() + delimiter + super.toString();
    }
}
