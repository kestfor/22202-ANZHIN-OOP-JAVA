package snake;

import java.awt.*;
import java.util.LinkedList;
import cell.Cell;

public class Snake {

    public enum Directions {
        left,
        right,
        up,
        down
    }

    private int speed;

    final private LinkedList<Cell> body;

    private Directions direction;


    public Snake(Cell start, Directions startDirection, int speed) {
        body = new LinkedList<>();
        this.direction = startDirection;
        this.speed = speed;
        add(start);
    }

    public Snake(Cell start, int speed) {
        this(start, Directions.right, speed);
    }

    public Snake(Cell start) {
        this(start, 10000000);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int newSpeed) {
        this.speed = newSpeed;
    }

    public void add(Cell cell) {
        Cell copy = cell.copy();
        body.add(copy);
    }

    public void setDirection(Directions newDirection) {
        this.direction = newDirection;
    }

    public void reset(Cell start, Directions startDirection) {
        body.clear();
        this.direction = startDirection;
        add(start);
    }

    public void move(Directions direction) {
        if (direction == null) {
            return;
        }
        Cell head = body.getFirst();
        switch (direction) {
            case up: {
                body.addFirst(new Cell(head.leftX, head.topY - head.size, head.size));
                this.direction = Directions.up;
                break;
            }
            case down: {
                body.addFirst(new Cell(head.leftX, head.topY + head.size, head.size));
                this.direction = Directions.down;
                break;
            }
            case left: {
                body.addFirst(new Cell(head.leftX - head.size, head.topY, head.size));
                this.direction = Directions.left;
                break;
            }
            case right: {
                body.addFirst(new Cell(head.leftX + head.size, head.topY, head.size));
                this.direction = Directions.right;
                break;
            }
            default: {
                return;
            }
        }
        body.removeLast();
    }

    public Cell getHead() {
        return body.getFirst();
    }

    public LinkedList<Cell> getBody() {
        return body;
    }

    public Cell[] getBodyArray() {
        Cell[] retVal = new Cell[body.size()];
        int ind = 0;
        for (Cell cell : body) {
            retVal[ind] = cell;
            ind++;
        }
        return retVal;
    }

    public Directions getDirection() {
        return direction;
    }

    public Cell getTail() {
        return body.getLast();
    }

}
