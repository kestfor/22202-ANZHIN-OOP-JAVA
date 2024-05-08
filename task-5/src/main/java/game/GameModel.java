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
    private int score;

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
        this.score = 0;

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
//        for (int i = 0; i < field.getArray().length; i++) {
//            for (int j = 0; j < field.getArray()[i].length; j++) {
//                if (field.getCell(i, j).equals(apple.getCell())) {
//                    return new Pair<>(i, j);
//                }
//            }
//        }
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

    public void setScore(int newScore) {
        this.score = newScore;
    }

    public int getScore() {
        return score;
    }

}
