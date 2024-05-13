package snake;

import cell.Cell;
import field.Field;

public class OnlineSnakeMoveController extends SnakeMovementController {

    public OnlineSnakeMoveController(Snake snake, Field field) {
        super(snake, field);
    }

    public boolean isNotCollisionWithSnake(Snake otherSnake) {
        switch (direction) {
            case right: {
                return notInOtherSnakeBody(snake.getHead().leftX + snake.getHead().size, snake.getHead().topY, otherSnake);
            }
            case left: {
                return notInOtherSnakeBody(snake.getHead().leftX - snake.getHead().size, snake.getHead().topY, otherSnake);
            }
            case up: {
                return notInOtherSnakeBody(snake.getHead().leftX, snake.getHead().topY - snake.getHead().size, otherSnake);
            }
            case down: {
                return notInOtherSnakeBody(snake.getHead().leftX, snake.getHead().topY + snake.getHead().size, otherSnake);
            }
        }
        return false;
    }

    protected boolean notInOtherSnakeBody(int leftX, int topY, Snake snake) {
        for (Cell cell : snake.getBody()) {
            if (cell.topY == topY && cell.leftX == leftX) {
                return false;
            }
        }
        return true;
    }

    public void setCollision() {
        collision = true;
    }
}
