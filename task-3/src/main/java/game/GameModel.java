package game;

import apple.Apple;
import field.Field;
import snake.Snake;
import utils.Utils;

public class GameModel {

    private final Snake snake;
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

    public GameModel(Settings settings) {
        int centerX = (settings.windowWidth - settings.getCellSize() * settings.getSize().second) / 2;
        int centerY = (settings.windowHeight - settings.getCellSize() * settings.getSize().first) / 2;

        this.gameState = GameState.init;
        this.score = 0;

        field = new Field(centerX, centerY, settings.getSize().first, settings.getSize().second, settings.getCellSize());
        snake = new Snake(field.getCell(0, 0), settings.getSnakeSpeed());
        apple = new Apple(Utils.getRandomAvailable(field.getArray(), snake.getBodyArray()));

    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState newGameState) {
        this.gameState = newGameState;
    }

    public Snake getSnake() {
        return snake;
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
