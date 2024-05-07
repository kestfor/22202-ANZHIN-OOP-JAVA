package socketGameMessage;

import clientSideGame.snake.Snake;
import events.Event;
import socketGameMessage.events.*;
import utils.Pair;

public class SocketGameMessage {
    private String message;

    public enum types {
        directionEvent,
        pauseEvent,
        startEvent,
        endEvent,
        restartEvent,
        newAppleEvent,
        newPlayerEvent,
        exitPlayerEvent
    }

    @Override
    public String toString() {
        return message;
    }

    public Event getEvent() {
        String[] tokens = message.split(" ");
        types type = types.values()[Integer.parseInt(tokens[0])];
        switch (type) {
            case directionEvent:
                return new DirectionEvent(Integer.parseInt(tokens[1]), Snake.Directions.values()[Integer.parseInt(tokens[2])]);
            case pauseEvent:
                return new PauseEvent(Integer.parseInt(tokens[1]));
            case startEvent:
                return new StartEvent(Integer.parseInt(tokens[1]));
            case endEvent:
                return new EndEvent(Integer.parseInt(tokens[1]));
            case restartEvent:
                return new RestartEvent(Integer.parseInt(tokens[1]));
            case newPlayerEvent:
                return new NewPlayerEvent(Integer.parseInt(tokens[1]));
            case exitPlayerEvent:
                return new ExitPlayerEvent(Integer.parseInt(tokens[1]));
            case newAppleEvent:
                return new NewAppleEvent(Integer.parseInt(tokens[1]), new Pair<>(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3])));
            default:
                return null;
        }
    }

    public SocketGameMessage(Event event) {
        if (event instanceof DirectionEvent) {
            this.message = types.directionEvent.ordinal() + " " + ((DirectionEvent) event).getSnakeNum() + " " + ((DirectionEvent) event).getDirection().ordinal();
        } else if (event instanceof PauseEvent) {
            this.message = types.pauseEvent.ordinal() + " " + ((PauseEvent) event).getClientId();
        } else if (event instanceof StartEvent) {
            this.message = types.startEvent.ordinal() + " " + ((StartEvent) event).getClientId();
        } else if (event instanceof EndEvent) {
            this.message = types.endEvent.ordinal() + " " + ((EndEvent) event).getClientId();
        } else if (event instanceof RestartEvent) {
            this.message = types.restartEvent.ordinal() + " " + ((RestartEvent) event).getClientId();
        } else if (event instanceof NewPlayerEvent) {
            this.message = types.newPlayerEvent.ordinal() + " " + ((NewPlayerEvent) event).getClientId();
        } else if (event instanceof ExitPlayerEvent) {
            this.message = types.exitPlayerEvent.ordinal() + " " + ((ExitPlayerEvent) event).getClientId();
        } else if (event instanceof NewAppleEvent) {
            this.message = types.newAppleEvent.ordinal() + " " + ((NewAppleEvent) event).getClientId() + " " + ((NewAppleEvent) event).getPosition().first + " " + ((NewAppleEvent) event).getPosition().second;
        }
    }


    public SocketGameMessage(String message) {
        this.message = message;
    }
}
