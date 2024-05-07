package socketGameMessage.events;
import events.Event;
import utils.Pair;

public class NewAppleEvent extends Event {
    private final int clientId;
    private final Pair<Integer, Integer> position;

    public NewAppleEvent(int clientId, Pair<Integer, Integer> position) {
        this.clientId = clientId;
        this.position = position;
    }

    public Pair<Integer, Integer> getPosition() {
        return position;
    }

    public int getClientId() {
        return clientId;
    }
}
