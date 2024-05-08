package game;

import client.Client;
import service.Observable;
import snake.Snake;
import socketGameMessage.events.*;
import sounds.SoundController;
import utils.Pair;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ClientGameController extends Observable implements KeyListener, Runnable {

    private final GameModel gameModel;
    private final GamePanel gamePanel;
    private final Client client;
    private final SoundController soundController;

    public ClientGameController(GameModel gameModel, GamePanel gamePanel, Client client) {
        this.gameModel = gameModel;
        this.gamePanel = gamePanel;
        this.client = client;
        this.addObserver(client);
        this.soundController = new SoundController();
    }

    private void standardKeyHandle(KeyEvent e) {
        if (gameModel.getGameState() == GameModel.GameState.over || gameModel.getGameState() == GameModel.GameState.win) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                notify(new RestartEvent(client.getClientId()));
            }
        } else if (gameModel.getGameState() == GameModel.GameState.init){
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                notify(new StartEvent(client.getClientId()));
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            notify(new PauseEvent(client.getClientId()));
        }

        Snake.Directions newDirection = getDirection(e);
        if (newDirection == null) {
            return;
        }

        Snake.Directions currentDirection = gameModel.getSnakesManager().getSnake(client.getClientId()).getDirection();

        if (currentDirection == newDirection) {
            return;
        }

        if (gameModel.getGameState() == GameModel.GameState.active) {
            notify(new DirectionEvent(client.getClientId(), newDirection));
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

    public void handleAppleEvent(Pair<Integer, Integer> coords) {
        if (gameModel.getGameState() == GameModel.GameState.active) {
            soundController.appleSound();
        }
        gameModel.getApple().setCell(gameModel.getField().getCell(coords.second, coords.first));
        gamePanel.repaint();
    }

    public void handlePauseEvent() {
        switch (gameModel.getGameState()) {
            case pause:
                unPauseGame();
                break;
            case active:
                pauseGame();
                break;
            default:
                break;
        }
    }

    public void handleStartEvent() {
        soundController.startMusic();
        this.gameModel.setGameState(GameModel.GameState.active);
        gamePanel.repaint();
    }

    public void pauseGame() {
        gameModel.setGameState(GameModel.GameState.pause);
        soundController.pauseMusic();
        gamePanel.repaint();
    }

    public void unPauseGame() {
        gameModel.setGameState(GameModel.GameState.active);
        soundController.resumeMusic();
        gamePanel.repaint();
    }

    public void handleEndEvent(int winner) {
        if (winner >= 0) {
            for (Integer id : gameModel.getSnakesManager().getIdsOfAliveSnakes()) {
                if (winner != id) {
                    gameModel.getSnakesManager().killSnake(id);
                }
            }
            gameModel.setGameState(GameModel.GameState.win);
        } else {
            gameModel.setGameState(GameModel.GameState.over);
            soundController.pauseMusic();
            soundController.crashSound();
        }
        gamePanel.repaint();

    }

    public void handleRestartEvent() {
        soundController.pauseMusic();
        this.gameModel.setScore(0);
        this.gameModel.getSnakesManager().reset();
        this.gameModel.setGameState(GameModel.GameState.init);
        gamePanel.repaint();

    }

    public void handleExitPlayerEvent(ExitPlayerEvent event) {
        gameModel.getSnakesManager().removeSnake(event.getClientId());
        gamePanel.repaint();

    }

    public void handleNewPlayerEvent(NewPlayerEvent event) {
        gameModel.getSnakesManager().addSnake(event.getClientId(), gameModel.getStartSpeed());
        gamePanel.repaint();
    }

    public void updateModel(TickUpdate update) {
        Pair<Integer, Integer> appleCoords = update.getApplePosition();
        gameModel.getApple().setCell(gameModel.getField().getCell(appleCoords.second, appleCoords.first));

        for (Integer snakeId : update.getSnakesPositions().keySet()) {
            Snake snake = gameModel.getSnakesManager().getSnake(snakeId);
            if (snake == null) {
                gameModel.getSnakesManager().addSnake(snakeId, gameModel.getStartSpeed());
                snake = gameModel.getSnakesManager().getSnake(snakeId);
            }

            snake.setBody(gameModel.getField(), update.getSnakesPositions().get(snakeId));
            snake.setDirection(update.getSnakesDirections().get(snakeId));
        }
        gamePanel.repaint();
    }

    @Override
    public void run() {

    }
}
