package events;

public class NewSnakeSpeedEvent extends Event{

    final int newSpeed;

    public NewSnakeSpeedEvent(int newSpeed) {
        this.newSpeed = newSpeed;
    }

    public int getNewSpeed() {
        return newSpeed;
    }
}
