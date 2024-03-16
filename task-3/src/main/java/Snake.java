import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class Snake implements Drawable{

    public enum Directions {
        left,
        right,
        up,
        down
    }

    private LinkedList<Cell> body;

    private Color color;

    private Directions direction;

    Snake(Cell start) {
        this(start, new Color(119, 119, 255), Directions.right);
    }

    Snake(Cell start, Color color, Directions direction) {
        this.color = color;
        body = new LinkedList<>();
        this.direction = direction;
        add(start);
    }

    public void add(Cell cell) {
        Cell copy = cell.copy();
        if (copy.getMainColor() != color || copy.getBorderColor() != color) {
            copy.setMainColor(color);
            copy.setBorderColor(color);
        }
        body.add(copy);
    }

    @Override
    public void draw(Graphics g) {
        int ind = 0;
        for (Cell cell : body) {
            if (ind == 0) {
                drawHead(g);
            } else {
                cell.draw(g);
            }
            ind++;
        }
    }

    private void drawHead(Graphics g) {
        Cell head = getHead();
        head.draw(g);
        int eyeSize = head.size / 4;
        int y;
        int x;
        switch (direction) {
            case right: {
                y = head.topY;
                x = head.leftX;
                break;
            }
            case left: {
                y = head.topY;
                x = head.rightX - eyeSize;
                break;
            }
            case up: {
                x = head.leftX;
                y = head.bottomY - eyeSize;
                break;
            }
            case down: {
                x = head.leftX;
                y = head.topY;
                break;
            }
            default: {
                y = head.topY;
                x = head.leftX;
            }
        }
        g.setColor(Color.white);
        if (direction == Directions.right || direction == Directions.left) {
            g.fillOval(x, head.topY + eyeSize / 2, eyeSize, eyeSize);
            g.fillOval(x, head.bottomY - eyeSize / 2 - eyeSize, eyeSize, eyeSize);
            g.setColor(Color.black);
            g.fillOval(x, head.topY + eyeSize / 2 + eyeSize / 4, eyeSize / 2, eyeSize / 2);
            g.fillOval(x, head.bottomY - eyeSize / 2 - eyeSize + eyeSize / 4, eyeSize / 2, eyeSize / 2);
        } else {
            g.fillOval(head.leftX + eyeSize / 2, y, eyeSize, eyeSize);
            g.fillOval(head.rightX - eyeSize - eyeSize / 2 , y, eyeSize, eyeSize);
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
                this.direction = Directions.up;
                break;
            }
            case down: {
                body.addFirst(new Cell(head.leftX, head.topY + head.size, head.size, head.getMainColor()));
                this.direction = Directions.down;
                break;
            }
            case left: {
                body.addFirst(new Cell(head.leftX - head.size, head.topY, head.size, head.getMainColor()));
                this.direction = Directions.left;
                break;
            }
            case right: {
                body.addFirst(new Cell(head.leftX + head.size, head.topY, head.size, head.getMainColor()));
                this.direction = Directions.right;
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

    LinkedList<Cell> getBody() {
        return body;
    }

    Cell[] getBodyArray() {
        Cell[] retVal = new Cell[body.size()];
        int ind = 0;
        for (Cell cell : body) {
            retVal[ind] = cell;
            ind++;
        }
        return retVal;
    }

    Cell getTail() {
        return body.getLast();
    }

}
