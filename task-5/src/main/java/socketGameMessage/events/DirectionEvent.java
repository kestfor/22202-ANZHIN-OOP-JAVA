package socketGameMessage.events;

import snake.Snake;
import socketGameMessage.SocketGameMessage;

public class DirectionEvent extends SocketEvent {
    protected final Snake.Directions direction;

    public DirectionEvent(int clientId, Snake.Directions direction) {
        super(clientId);
        this.direction = direction;
    }

    public DirectionEvent(String rawString) {
        String[] tokens = rawString.split(String.valueOf(SocketEvent.delimiter));
        this.clientId = Integer.parseInt(tokens[0]);
        this.direction = Snake.Directions.values()[Integer.parseInt(tokens[1])];
    }

    public Snake.Directions getDirection() {
        return direction;
    }

    public String toString() {
        return SocketGameMessage.types.directionEvent.ordinal() + delimiter + super.toString() + SocketEvent.delimiter + direction.ordinal();
    }
}
