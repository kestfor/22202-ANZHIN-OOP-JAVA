package clientSideGame.game;

import cell.Cell;
import clientSideGame.apple.Apple;
import clientSideGame.field.Field;
import clientSideGame.snake.SnakesManager;
import clientSideGame.utils.Utils;
import utils.Pair;

public class GameModel {

    private final SnakesManager snakesManager;
    final private Field field;
    private final Apple apple;

    private GameState gameState;
    private int score;

    public enum GameState {
        init,
        active,
        pause,
        over,
        win
    }

    public GameModel(Settings settings, int clientId) {
        int centerX = (settings.windowWidth - settings.getCellSize() * settings.getSize().second) / 2;
        int centerY = (settings.windowHeight - settings.getCellSize() * settings.getSize().first) / 2;

        this.gameState = GameState.init;
        this.score = 0;

        field = new Field(centerX, centerY, settings.getSize().first, settings.getSize().second, settings.getCellSize());
        snakesManager = new SnakesManager(field);
        for (int i = 0; i <= clientId; i++) {
            snakesManager.addSnake(i, settings.getSnakeSpeed());
        }
        apple = new Apple(Utils.getRandomAvailable(field.getArray(), snakesManager.getTakenCells()));

    }

    public GameState getGameState() {
        return gameState;
    }

    public Pair<Integer, Integer> getApplePosition() {
        for (int i = 0; i < field.getArray().length; i++) {
            for (int j = 0; j < field.getArray()[i].length; j++) {
                if (field.getCell(i, j).equals(apple.getCell())) {
                    return new Pair<>(i, j);
                }
            }
        }
        return new Pair<>(-1, -1);
    }

    public void setGameState(GameState newGameState) {
        this.gameState = newGameState;
    }

    public SnakesManager getSnakesManager() {
        return snakesManager;
    }

    public Field getField() {
        return field;
    }

    public Apple getApple() {
        return apple;
    }

    public void setScore(int newScore) {
        this.score = newScore;
    }

    public int getScore() {
        return score;
    }

}
