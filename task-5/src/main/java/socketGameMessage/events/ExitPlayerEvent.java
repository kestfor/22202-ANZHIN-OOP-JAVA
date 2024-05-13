package socketGameMessage.events;


import socketGameMessage.SocketGameMessage;

public class ExitPlayerEvent extends SocketEvent {

    public ExitPlayerEvent(int clientId) {
        super(clientId);
    }

    public ExitPlayerEvent(String rawString) {
        super(rawString);
    }

    @Override
    public String toString() {
        return SocketGameMessage.types.exitPlayerEvent.ordinal() + delimiter + super.toString();
    }
}
