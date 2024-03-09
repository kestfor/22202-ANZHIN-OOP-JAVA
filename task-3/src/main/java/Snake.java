import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class Snake {

    public enum Directions {
        left,
        right,
        up,
        down
    }

    private LinkedList<Cell> body;

    private Color color;

    Snake(Cell start) {
        this(start, Color.red);
    }

    Snake(Cell start, Color color) {
        this.color = color;
        Cell copy = start.copy();
        if (copy.getMainColor() != color) {
            copy.setMainColor(color);
        }
        body = new LinkedList<Cell>();
        body.push(copy);
    }

    public void add(Cell cell) {
        Cell copy = cell.copy();
        if (copy.getMainColor() != color) {
            copy.setMainColor(color);
        }
        body.add(copy);
    }

    public void draw(Graphics g) {
        for (Cell cell : body) {
            cell.paintComponent(g);
        }
    }

    public void move(Directions direction) {
        if (direction == null) {
            return;
        }
        Cell head = body.getFirst();
        switch (direction) {
            case up: {
                body.addFirst(new Cell(head.leftX, head.topY - head.size, head.size, head.getMainColor()));
                break;
            }
            case down: {
                body.addFirst(new Cell(head.leftX, head.topY + head.size, head.size, head.getMainColor()));
                break;
            }
            case left: {
                body.addFirst(new Cell(head.leftX - head.size, head.topY, head.size, head.getMainColor()));
                break;
            }
            case right: {
                body.addFirst(new Cell(head.leftX + head.size, head.topY, head.size, head.getMainColor()));
                break;
            }
            default: {
                return;
            }
        }
        body.removeLast();
    }

    Cell getHead() {
        return body.getFirst();
    }

}
