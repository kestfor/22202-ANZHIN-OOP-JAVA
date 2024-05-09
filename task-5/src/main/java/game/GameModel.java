package game;

import apple.Apple;
import cell.Cell;
import field.Field;
import snake.SnakesManager;
import utils.Utils;
import utils.Pair;

public class GameModel {

    private final SnakesManager snakesManager;
    final private Field field;
    private final Apple apple;
    private final int startSpeed;
    private GameState gameState;

    public enum GameState {
        init,
        active,
        pause,
        over,
        win
    }

    public GameModel(Settings settings) {
        this.startSpeed = settings.getSnakeSpeed();
        int centerX = (settings.windowWidth - settings.getCellSize() * settings.getSize().second) / 2;
        int centerY = (settings.windowHeight - settings.getCellSize() * settings.getSize().first) / 2;

        this.gameState = GameState.init;

        field = new Field(centerX, centerY, settings.getSize().first, settings.getSize().second, settings.getCellSize());
        snakesManager = new SnakesManager(field);
        Cell[] taken = new Cell[0];
        apple = new Apple(Utils.getRandomAvailable(field.getArray(), taken));

    }

    public int getStartSpeed() {
        return startSpeed;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Pair<Integer, Integer> getApplePosition() {
        return apple.getCoords(field);
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

}
