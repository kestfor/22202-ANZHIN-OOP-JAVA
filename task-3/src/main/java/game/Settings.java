package game;
import service.Observable;
import service.Observer;
import utils.Pair;

public class Settings extends Observable implements Observer {

    private Pair<Integer, Integer> fieldSize;
    private int snakeSpeed;
    private final int cellSize;
    public final int windowHeight;
    public final int windowWidth;


    public Settings(Pair<Integer, Integer> fieldSize, int cellSize, int windowHeight, int windowWidth, int snakeSpeed) {
        this.setSize(fieldSize);
        this.cellSize = cellSize;
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
        this.snakeSpeed = snakeSpeed;
    }

    public void setSize(Pair<Integer, Integer> newSize) throws IllegalArgumentException {
        if (newSize.first <= 0 || newSize.second <= 0) {
            throw new IllegalArgumentException("rows and columns should be positive numbers");
        } else {
            this.fieldSize = newSize;
        }
    }

    public Pair<Integer, Integer> getSize() {
        return fieldSize;
    }

    public void setSnakeSpeed(int speed) {
        if (speed <= 0) {
            throw new IllegalArgumentException("speed should be positive number");
        } else {
            this.snakeSpeed = speed;
        }
    }

    public int getSnakeSpeed() {
        return snakeSpeed;
    }

    public int getCellSize() {
        return cellSize;
    }

}
