import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class Game implements KeyListener, Runnable {
    final private JFrame frame;
    final private DrawController drawController;

    private Snake snake;

    private SnakeMoveController snakeMoveController;

    final private Field field;

    private Apple apple;

    final private int FPS = 60;

    Game(Settings settings) {
        int centerX = (settings.windowWidth - settings.cellSize * settings.columns) / 2;
        int centerY = (settings.windowHeight - settings.cellSize * settings.rows) / 2;

        field = new Field(centerX, centerY, settings.columns, settings.rows, settings.cellSize);

        snake = new Snake(field.getCell(0, 0));

        apple = new Apple(getRandomAvailable(field.getArray(), snake.getBodyArray()));

        snakeMoveController = new SnakeMoveController(snake, field, 5000000);

        drawController = new DrawController(settings.windowWidth, settings.windowHeight, Arrays.asList(field, apple, snake));

        frame = new JFrame("Snake");
        frame.addKeyListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);


        frame.add(drawController);

        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true)  ;
    }

    private static boolean isFree(Cell cellToCheck, Cell[] taken) {
        for (Cell value : taken) {
            if (value.equals(cellToCheck)) {
                return false;
            }
        }
        return true;
    }

    static public Cell getRandomAvailable(Cell[][] field, Cell[] taken) {

        int startRow = (int) Math.floor(Math.random() *(field.length));
        int startColumn = (int) Math.floor(Math.random() * (field[0].length));
        for (int i = startRow; i < field.length; i++) {
            for (int j = startColumn; j < field[i].length; j++) {
                if (isFree(field[i][j], taken)) {
                    return field[i][j];
                }
            }
        }

        for (int i = 0; i < startRow; i++) {
            for (int j = 0; j < startColumn; j++) {
                if (isFree(field[i][j], taken)) {
                    return field[i][j];
                }
            }
        }

        return null;
    }

    private void standardKeyHandle(KeyEvent e) {
        if (drawController.isOver) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                snake = new Snake(field.getCell(0, 0));
                apple.setCell(getRandomAvailable(field.getArray(), snake.getBodyArray()));
                drawController.reset();
                drawController.setObjects(Arrays.asList(field, apple, snake));
                snakeMoveController = new SnakeMoveController(snake, field, 5000000);
                drawController.isOver = false;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            drawController.isPaused = !drawController.isPaused;
        }

        Snake.Directions lastDirection = getDirection(e);
        if (lastDirection == null) {
            return;
        }
        if (!drawController.isPaused) {
            snakeMoveController.setDirection(lastDirection);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        standardKeyHandle(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
       standardKeyHandle(e);
    }


    @Override
    public void run() {
        double drawInterval = (double) 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while(true) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (!drawController.isPaused && !drawController.isOver) {
                if (!snakeMoveController.isCollision()) {
                    Cell tail = snakeMoveController.move();
                    if (tail != null) {
                        if (snake.getHead().equals(apple.cell)) {
                            snake.add(tail);
                            drawController.setScore(drawController.getScore() + 1);
                            Cell newCell = getRandomAvailable(field.getArray(), snake.getBodyArray());
                            if (newCell != null) {
                                apple.setCell(newCell);
                            } else {
                                drawController.isOver = true;
                            }
                        }
                    }
                } else {
                    drawController.isOver = true;
                }
            }

            if (delta >= 1) {
                drawController.repaint();
                delta--;
            }
        }
    }



        private Snake.Directions getDirection(KeyEvent e) {
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
