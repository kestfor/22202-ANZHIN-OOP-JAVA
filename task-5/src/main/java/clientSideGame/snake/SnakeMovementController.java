package clientSideGame.snake;
import clientSideGame.cell.Cell;
import clientSideGame.field.Field;

public class SnakeMovementController {
    protected Snake.Directions direction;
    protected final Snake snake;

    protected boolean collision = false;
    protected final Field field;

    public SnakeMovementController(Snake snake, Field field) {
        this.snake = snake;
        this.field = field;
        this.direction = snake.getDirection();
    }


    public void restart(Snake.Directions direction) {
        this.collision = false;
        this.direction = direction;
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

    protected boolean checkFieldCollision(Snake.Directions direction, Cell cell, Field field) {
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

    protected boolean checkSnakeCollision(Snake.Directions direction, Snake snake) {
        switch (direction) {
            case right: {
                return notInBody(snake.getHead().leftX + snake.getHead().size, snake.getHead().topY, snake);
            }
            case left: {
                return notInBody(snake.getHead().leftX - snake.getHead().size, snake.getHead().topY, snake);
            }
            case up: {
                return notInBody(snake.getHead().leftX, snake.getHead().topY - snake.getHead().size, snake);
            }
            case down: {
                return notInBody(snake.getHead().leftX, snake.getHead().topY + snake.getHead().size, snake);
            }
        }
        return false;
    }

    protected boolean notInBody(int leftX, int topY, Snake snake) {
        for (Cell cell : snake.getBody()) {
            if (cell.topY == topY && cell.leftX == leftX && !cell.equals(snake.getTail())) {
                return false;
            }
        }
        return true;
    }

    //returns tail cell of snake that was moved
    public Cell move() {
        snake.setDirection(direction);
        if (checkFieldCollision(direction, snake.getHead(), field) && checkSnakeCollision(direction, snake)) {
            Cell returnVal = snake.getTail().copy();
            snake.move(direction);
            return returnVal;
        } else {
            collision = true;
            return null;
        }
    }

    public boolean isCollision() {
        return collision;
    }

}
