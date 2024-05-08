package socketGameMessage.events;
import events.Event;
import socketGameMessage.SocketGameMessage;
import utils.Pair;

public class NewAppleEvent extends SocketEvent {
    private final Pair<Integer, Integer> position;

    public NewAppleEvent(Pair<Integer, Integer> position) {
        this.position = position;
        this.clientId = -1;
    }

    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    public NewAppleEvent(String rawString) {
        String[] tokens = rawString.split(String.valueOf(SocketEvent.delimiter));
        this.clientId = -1;
        this.position = new Pair<>(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
    }

    public String toString() {
        return SocketGameMessage.types.newAppleEvent.ordinal() + SocketEvent.delimiter + position.first + SocketEvent.delimiter + position.second;
    }
}
