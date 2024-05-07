package socketGameMessage.events;

import clientSideGame.snake.Snake;
import events.Event;

public class DirectionEvent extends Event {
    private final Snake.Directions direction;
    private final int snakeNum;

    public DirectionEvent(int snakeNum, Snake.Directions direction) {
        this.snakeNum = snakeNum;
        this.direction = direction;
    }

    public Snake.Directions getDirection() {
        return direction;
    }

    public int getSnakeNum() {
        return snakeNum;
    }
}
