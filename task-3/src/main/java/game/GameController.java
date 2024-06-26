package game;

import cell.Cell;
import snake.Snake;
import snake.SnakeMovementController;
import sounds.SoundController;
import utils.Utils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController implements Runnable, KeyListener {

    private final GameModel gameModel;
    private final SnakeMovementController snakeMovementController;

    private final SoundController soundController;
    private final GamePanel gamePanel;

    public GameController(GameModel gameModel, GamePanel gamePanel) {
        this.gameModel = gameModel;
        this.gamePanel = gamePanel;
        this.snakeMovementController = new SnakeMovementController(gameModel.getSnake(), gameModel.getField());
        this.soundController = new SoundController();
    }

    public void restart() {
        this.gameModel.setScore(0);
        this.gameModel.getSnake().reset(this.gameModel.getField().getCell(0, 0), Snake.Directions.right);
        this.gameModel.getApple().setCell(Utils.getRandomAvailable(this.gameModel.getField().getArray(), this.gameModel.getSnake().getBodyArray()));
        this.snakeMovementController.restart();
        this.gameModel.setGameState(GameModel.GameState.init);
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000/60;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (true) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            GameModel.GameState currGameState = gameModel.getGameState();

            if (currGameState == GameModel.GameState.active) {
                if (!snakeMovementController.isCollision()) {
                    Cell tail = snakeMovementController.move();
                    if (tail != null) {
                        if (gameModel.getSnake().getHead().equals(gameModel.getApple().getCell())) {
                            soundController.appleSound();
                            gameModel.getSnake().add(tail);
                            gameModel.setScore(gameModel.getScore() + 1);
                            Cell newCell = Utils.getRandomAvailable(gameModel.getField().getArray(), gameModel.getSnake().getBodyArray());
                            if (newCell != null) {
                                gameModel.getApple().setCell(newCell);
                            } else {
                                if (gameModel.getSnake().getBody().size() == gameModel.getField().getAmountColumns() * gameModel.getField().getAmountRows()) {
                                    gameModel.setGameState(GameModel.GameState.win);
                                } else {
                                    gameModel.setGameState(GameModel.GameState.over);
                                }
                            }
                        }
                    }
                } else {
                    gameModel.setGameState(GameModel.GameState.over);
                    soundController.pauseMusic();
                    soundController.crashSound();
                }
            }

            if (delta >= 1) {
                gamePanel.repaint();
                delta--;
            }
        }
    }

    private void standardKeyHandle(KeyEvent e) {
        if (gameModel.getGameState() == GameModel.GameState.over || gameModel.getGameState() == GameModel.GameState.win) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                soundController.pauseMusic();
                this.restart();
            }
        } else if (gameModel.getGameState() == GameModel.GameState.init){
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                soundController.startMusic();
                this.gameModel.setGameState(GameModel.GameState.active);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            switch (gameModel.getGameState()) {
                case pause:
                    gameModel.setGameState(GameModel.GameState.active);
                    soundController.resumeMusic();
                    break;
                case active:
                    gameModel.setGameState(GameModel.GameState.pause);
                    soundController.pauseMusic();
                    break;
            }
        }

        Snake.Directions lastDirection = getDirection(e);
        if (lastDirection == null) {
            return;
        }
        if (gameModel.getGameState() == GameModel.GameState.active) {

            snakeMovementController.setDirection(lastDirection);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        standardKeyHandle(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        standardKeyHandle(e);
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
