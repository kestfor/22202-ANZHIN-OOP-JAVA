public class SnakeMoveController {
    private Snake.Directions direction;

    private final long timeToMove;

    private long lastMoveTime;
    private final Snake snake;

    private boolean collision = false;


    private final Field field;

    public SnakeMoveController(Snake snake, Field field, long timeToMove) {
        this.snake = snake;
        this.field = field;
        this.direction =  Snake.Directions.right;
        this.lastMoveTime = 0;
        this.timeToMove = timeToMove;
    }

    public void reset() {
        this.lastMoveTime = 0;
        this.collision = false;
    }

    public void setDirection(Snake.Directions direction) {
        switch (direction) {
            case up: {
                if (this.direction == Snake.Directions.down) {
                    return;
                }
                break;
            }
            case down: {
                if (this.direction == Snake.Directions.up) {
                    return;
                }
                break;
            }
            case left: {
                if (this.direction == Snake.Directions.right) {
                    return;
                }
                break;
            }
            case right: {
                if (this.direction == Snake.Directions.left) {
                    return;
                }
                break;
            }
        }
        this.direction = direction;
    }

    private boolean checkFieldCollision(Snake.Directions direction, Cell cell, Field field) {
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

    private boolean checkSnakeCollision(Snake.Directions direction, Snake snake) {
        switch (direction) {
            case right: {
                return !inBody(snake.getHead().leftX + snake.getHead().size, snake.getHead().topY, snake);
            }
            case left: {
                return !inBody(snake.getHead().leftX - snake.getHead().size, snake.getHead().topY, snake);
            }
            case up: {
                return !inBody(snake.getHead().leftX, snake.getHead().topY - snake.getHead().size, snake);
            }
            case down: {
                return !inBody(snake.getHead().leftX, snake.getHead().topY + snake.getHead().size, snake);
            }
        }
        return false;
    }

    private boolean inBody(int leftX, int topY, Snake snake) {
        for (Cell cell : snake.getBody()) {
            if (cell.topY == topY && cell.leftX == leftX) {
                return true;
            }
        }
        return false;
    }

    public Cell move() {
        if (lastMoveTime >= timeToMove) {
            if (checkFieldCollision(direction, snake.getHead(), field) && checkSnakeCollision(direction, snake)) {
                Cell returnVal = snake.getTail().copy();
                snake.move(direction);
                lastMoveTime = 0;
                return returnVal;
            } else {
                collision = true;
            }
            lastMoveTime = 0;
        } else {
            lastMoveTime++;
        }
        return null;
    }

    public boolean isCollision() {
        return collision;
    }

}
