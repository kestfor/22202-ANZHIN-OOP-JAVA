package clientSideGame.game;

import client.Client;
import clientSideGame.cell.Cell;
import clientSideGame.snake.Snake;
import clientSideGame.sounds.SoundController;
import clientSideGame.utils.Utils;
import service.Observable;
import socketGameMessage.events.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameController extends Observable implements Runnable, KeyListener, ActionListener {

    private final GameModel gameModel;
    private final Timer timer;
    private final SoundController soundController;
    private final GamePanel gamePanel;
    private final Client client;

    public GameController(GameModel gameModel, GamePanel gamePanel, Client client) {
        this.timer = new Timer(gameModel.getSnakesManager().getSpeed(), this);
        this.gameModel = gameModel;
        this.gamePanel = gamePanel;
        this.client = client;
        this.soundController = new SoundController();
    }

    public void restart() {
        soundController.pauseMusic();
        this.gameModel.setScore(0);
        this.gameModel.getSnakesManager().reset();
        this.gameModel.getApple().setCell(Utils.getRandomAvailable(this.gameModel.getField().getArray(), this.gameModel.getSnakesManager().getTakenCells()));
        notify(new NewAppleEvent(client.getClientId(), gameModel.getApplePosition()));
        this.gameModel.setGameState(GameModel.GameState.init);
        this.timer.setDelay(gameModel.getSnakesManager().getSpeed());
    }

    @Override
    public void run() {

    }

    public void startGame() {
        if (this.timer.isRunning()) {
            this.timer.stop();
        }
        this.timer.start();
        soundController.startMusic();
        this.gameModel.setGameState(GameModel.GameState.active);
    }

    public void pauseGame() {
        gameModel.setGameState(GameModel.GameState.pause);
        soundController.pauseMusic();
    }

    public void unPauseGame() {
        if (this.timer.isRunning()) {
            this.timer.stop();
        }
        this.timer.start();
        gameModel.setGameState(GameModel.GameState.active);
        soundController.resumeMusic();
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

        Snake.Directions lastDirection = getDirection(e);
        if (lastDirection == null) {
            return;
        }

        Snake.Directions currentDirection = gameModel.getSnakesManager().getSnake(client.getClientId()).getDirection();

        if (currentDirection == lastDirection) {
            return;
        }

        if (gameModel.getGameState() == GameModel.GameState.active) {
            notify(new DirectionEvent(client.getClientId(), lastDirection));
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

    @Override
    public void actionPerformed(ActionEvent e) {

        if (gameModel.getSnakesManager().getNumOfActive() == 1 && gameModel.getSnakesManager().getSnakesNum() > 1) {
            gameModel.setGameState(GameModel.GameState.win);
        }

        GameModel.GameState currGameState = gameModel.getGameState();

        if (currGameState == GameModel.GameState.active) {
            ArrayList<Cell> tails = gameModel.getSnakesManager().move();
            boolean isOver = true;
            for (Cell cell : tails) {
                if (cell != null) {
                    isOver = false;
                }
            }
            if (isOver) {
                gameModel.setGameState(GameModel.GameState.over);
                soundController.crashSound();
                soundController.pauseMusic();
            }

            int snakeWithApple = gameModel.getSnakesManager().getSnakeNumByHead(gameModel.getApple().getCell());
            if (snakeWithApple != -1) {
                soundController.appleSound();
                gameModel.getSnakesManager().getSnake(snakeWithApple).add(tails.get(snakeWithApple));
                Cell newCell = Utils.getRandomAvailable(gameModel.getField().getArray(), gameModel.getSnakesManager().getTakenCells());
                if (newCell != null) {
                    gameModel.getApple().setCell(newCell);
                    if (snakeWithApple == client.getClientId()) {
                        notify(new NewAppleEvent(client.getClientId(), gameModel.getApplePosition()));
                    }
                } else {
                    if (gameModel.getSnakesManager().getTakenCells().length == gameModel.getField().getAmountColumns() * gameModel.getField().getAmountRows()) {
                        gameModel.setGameState(GameModel.GameState.win);
                    } else {
                        gameModel.setGameState(GameModel.GameState.over);
                    }
                }
            }
        }
    }
}
