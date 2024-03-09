import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements KeyListener, Runnable {
    final private JFrame frame;
    final private GamePanel gamePanel;

    final private Snake snake;

    final private SnakeMoveController snakeMoveController;

    private Snake.Directions lastDirection = Snake.Directions.right;

    final private Field field;

    final private int FPS = 60;

    Game(Settings settings) {
        int centerX = (settings.windowWidth - settings.cellSize * settings.columns) / 2;
        int centerY = (settings.windowHeight - settings.cellSize * settings.rows) / 2;

        field = new Field(centerX, centerY, settings.columns, settings.rows, settings.cellSize);
        snake = new Snake(field.getCell(0, 0));
        snakeMoveController = new SnakeMoveController(snake, field, 10000000);

        gamePanel = new GamePanel(settings.windowWidth, settings.windowHeight, snake, field);

        frame = new JFrame("Snake");
        frame.addKeyListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);


        frame.add(gamePanel);
        //frame.add(field);
//        frame.add(snake);

        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true)  ;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        lastDirection = handleKey(e);
        if (lastDirection == null) {
            return;
        }
        snakeMoveController.setDirection(lastDirection);
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        lastDirection = handleKey(e);
        if (lastDirection == null) {
            return;
        }
        snakeMoveController.setDirection(lastDirection);
    }


    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while(true) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (!snakeMoveController.isCollision()) {
                snakeMoveController.move();
            } else {
                gamePanel.isOver = true;
            }

            if (delta >= 1) {
                gamePanel.repaint();
                delta--;
            }

        }
    }

        private Snake.Directions handleKey(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                return Snake.Directions.left;
            case KeyEvent.VK_RIGHT:
                return Snake.Directions.right;
            case KeyEvent.VK_UP:
                return Snake.Directions.up;
            case KeyEvent.VK_DOWN:
                return Snake.Directions.down;
            default:
                return null;
        }
    }
}
