package game;
import cell.Cell;
import utils.Utils;
import service.Observable;
import socketGameMessage.events.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerGameController extends Observable implements ActionListener {

    private final GameModel gameModel;
    private final Timer timer;

    public ServerGameController(GameModel gameModel) {
        this.timer = new Timer(gameModel.getStartSpeed(), this);
        this.gameModel = gameModel;
    }

    public void restart() {
        this.gameModel.setScore(0);
        this.gameModel.getSnakesManager().reset();
        this.gameModel.getApple().setCell(Utils.getRandomAvailable(this.gameModel.getField().getArray(), this.gameModel.getSnakesManager().getTakenCells()));
        notify(new NewAppleEvent(gameModel.getApplePosition()));
        this.gameModel.setGameState(GameModel.GameState.init);
        this.timer.setDelay(gameModel.getStartSpeed());
    }

    public void startGame() {
        if (this.timer.isRunning()) {
            this.timer.stop();
        }
        this.timer.start();
        this.gameModel.setGameState(GameModel.GameState.active);
    }

    public void pauseGame() {
        if (this.timer.isRunning()) {
            this.timer.stop();
        }
        gameModel.setGameState(GameModel.GameState.pause);
    }


    public void unPauseGame() {
        if (this.timer.isRunning()) {
            this.timer.stop();
        }
        this.timer.start();
        gameModel.setGameState(GameModel.GameState.active);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (gameModel.getGameState() != GameModel.GameState.active) {
            return;
        }

        if (gameModel.getSnakesManager().getNumOfActive() == 1 && gameModel.getSnakesManager().getSnakesNum() > 1) {
            gameModel.setGameState(GameModel.GameState.win);
            ArrayList<Integer> ids = gameModel.getSnakesManager().getIdsOfAliveSnakes();
            notify(new EndEvent(ids.get(0)));
        }

        GameModel.GameState currGameState = gameModel.getGameState();

        if (currGameState == GameModel.GameState.active) {
            HashMap<Integer, Cell> tails = gameModel.getSnakesManager().move();
            boolean isOver = true;
            for (Cell cell : tails.values()) {
                if (cell != null) {
                    isOver = false;
                    break;
                }
            }
            if (isOver) {
                gameModel.setGameState(GameModel.GameState.over);
                notify(new EndEvent(EndEvent.endType.lose.getValue()));
            }

            int snakeWithApple = gameModel.getSnakesManager().getSnakeNumByHead(gameModel.getApple().getCell());

            if (snakeWithApple != -1) {
                System.out.println(snakeWithApple);
                System.out.println(gameModel.getSnakesManager().getIdsOfAliveSnakes());
                //soundController.appleSound();
                gameModel.getSnakesManager().getSnake(snakeWithApple).add(tails.get(snakeWithApple));
                Cell newCell = Utils.getRandomAvailable(gameModel.getField().getArray(), gameModel.getSnakesManager().getTakenCells());
                if (newCell != null) {
                    gameModel.getApple().setCell(newCell);
                    notify(new NewAppleEvent(gameModel.getApplePosition()));
                } else {
                    if (gameModel.getSnakesManager().getTakenCells().length == gameModel.getField().getAmountColumns() * gameModel.getField().getAmountRows()) {
                        gameModel.setGameState(GameModel.GameState.win);
                        notify(new EndEvent(EndEvent.endType.draw.getValue()));
                    } else {
                        gameModel.setGameState(GameModel.GameState.over);
                        notify(new EndEvent(EndEvent.endType.lose.getValue()));
                    }
                }
            }

            notify(new TickUpdate(gameModel.getSnakesManager().getSnakesCoords(), gameModel.getSnakesManager().getSnakesDirections(), gameModel.getApplePosition()));
        }
    }

    public void handleNewPlayerEvent(NewPlayerEvent event) {
        gameModel.getSnakesManager().addSnake(event.getClientId(), gameModel.getStartSpeed());
        notify(event);
        TickUpdate tickUpdate = new TickUpdate(gameModel.getSnakesManager().getSnakesCoords(), gameModel.getSnakesManager().getSnakesDirections(), gameModel.getApplePosition());
        notify(tickUpdate);
    }

    public void handleExitPlayerEvent(ExitPlayerEvent event) {
        notify(event);
        gameModel.getSnakesManager().removeSnake(event.getClientId());
    }

    public void handleDirectionEvent(DirectionEvent event) {
        this.gameModel.getSnakesManager().getController(event.getClientId()).setDirection(event.getDirection());
        TickUpdate tickUpdate = new TickUpdate(gameModel.getSnakesManager().getSnakesCoords(), gameModel.getSnakesManager().getSnakesDirections(), gameModel.getApplePosition());
        notify(tickUpdate);
    }

    public void handleRestartEvent(RestartEvent event) {
        restart();
        notify(event);
        TickUpdate tickUpdate = new TickUpdate(gameModel.getSnakesManager().getSnakesCoords(), gameModel.getSnakesManager().getSnakesDirections(), gameModel.getApplePosition());
        notify(tickUpdate);
    }

    public void handleStartEvent(StartEvent event) {
        notify(event);
        startGame();
    }

    public void handlePauseEvent(PauseEvent event) {
        switch (gameModel.getGameState()) {
            case pause:
                notify(event);
                unPauseGame();
                break;
            case active:
                notify(event);
                pauseGame();
                break;
            default:
                break;
        }
    }
}
