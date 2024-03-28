package snake;
import cell.Cell;
import painter.*;

import java.awt.*;

public class SnakePainter implements Painter {

    final Snake snake;

    public SnakePainter(Snake snake) {
        this.snake = snake;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(48, 102, 248));
        for (Cell cell : snake.getBody()) {
            g.fillRect(cell.leftX, cell.topY, cell.size, cell.size);
        }
        drawHead(g);
    }

        private void drawHead(Graphics g) {
        g.setColor(new Color(48, 102, 248));
        Cell head = snake.getHead();
        g.fillRect(head.leftX, head.topY, head.size, head.size);
        int eyeSize = head.size / 4;
        g.setColor(Color.white);

        if (snake.getDirection() == Snake.Directions.right || snake.getDirection() == Snake.Directions.left) {
            int x = snake.getDirection() == Snake.Directions.right ? head.leftX : head.rightX - eyeSize;

            g.fillOval(x, head.topY + eyeSize / 2, eyeSize, eyeSize);
            g.fillOval(x, head.bottomY - eyeSize / 2 - eyeSize, eyeSize, eyeSize);
            g.setColor(Color.black);

            int eyeBottomY = head.bottomY - eyeSize / 2 - eyeSize + eyeSize / 4;
            int eyeTopY = head.topY + eyeSize / 2 + eyeSize / 4;

            switch (snake.getDirection()) {
                case left:
                    g.fillOval(x, eyeTopY, eyeSize / 2, eyeSize / 2);
                    g.fillOval(x, eyeBottomY, eyeSize / 2, eyeSize / 2);
                    break;
                case right:
                    g.fillOval(x + eyeSize / 2, eyeTopY, eyeSize / 2, eyeSize / 2);
                    g.fillOval(x + eyeSize / 2, eyeBottomY, eyeSize / 2, eyeSize / 2);
                    break;
            }
        } else {
            int y = snake.getDirection() == Snake.Directions.up ? head.bottomY - eyeSize : head.topY;

            g.fillOval(head.leftX + eyeSize / 2, y, eyeSize, eyeSize);
            g.fillOval(head.rightX - eyeSize - eyeSize / 2 , y, eyeSize, eyeSize);

            int eyeLeftX = head.leftX + eyeSize / 4 + eyeSize / 2;
            int eyeRightX = head.rightX - eyeSize - eyeSize / 2 + eyeSize / 4;

            g.setColor(Color.black);


            switch (snake.getDirection()) {

                case up:
                    g.fillOval(eyeLeftX, head.bottomY - eyeSize, eyeSize / 2, eyeSize / 2);
                    g.fillOval(eyeRightX, head.bottomY - eyeSize, eyeSize / 2, eyeSize / 2);
                    break;
                case down:
                    g.fillOval(eyeLeftX, head.topY + eyeSize / 2, eyeSize / 2, eyeSize / 2);
                    g.fillOval(eyeRightX, head.topY + eyeSize / 2, eyeSize / 2, eyeSize / 2);
                    break;
            }
        }
    }

}
