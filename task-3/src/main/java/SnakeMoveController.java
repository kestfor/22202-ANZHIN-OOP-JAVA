public class SnakeMoveController {
    private Snake.Directions direction = Snake.Directions.right;

    private final long timeToMove;

    private long lastMoveTime;
    private final Snake snake;

    private boolean collision = false;

    private final Field field;

    public SnakeMoveController(Snake snake, Field field, long timeToMove) {
        this.snake = snake;
        this.field = field;
        lastMoveTime = 0;
        this.timeToMove = timeToMove;
    }

    public void setDirection(Snake.Directions direction) {
        this.direction = direction;
    }

    private boolean checkCollision(Snake.Directions direction, Cell cell, Field field) {
        switch (direction) {
            case up: {
                return cell.topY > field.getTopY();
            }
            case right: {
                return cell.rightX < field.getRightX();
            }
            case left: {
                return cell.leftX > field.getLeftX();
            }
            case down: {
                return cell.bottomY < field.getBottomY();
            }
        }
        return true;
    }

    public void move() {
        if (lastMoveTime >= timeToMove) {
            if (checkCollision(direction, snake.getHead(), field)) {
                snake.move(direction);
            } else {
                collision = true;
            }
            lastMoveTime = 0;
        } else {
            lastMoveTime++;
        }
    }

    public boolean isCollision() {
        return collision;
    }

}
