package snake;

import cell.Cell;
import field.Field;
import utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SnakesManager {
    public static final int maxNum = 4;
    HashMap<Integer, Pair<Snake, OnlineSnakeMoveController>> snakesData;
    private final Field field;

    public enum StartLocation {
        topLeft, topRight, bottomLeft, bottomRight
    }


    public SnakesManager(Field field) {
        this.field = field;
        this.snakesData = new HashMap<>();
    }

    public void removeSnake(int id) {
        this.snakesData.remove(id);
    }

    public HashMap<Integer, Snake.Directions> getSnakesDirections() {
        HashMap<Integer, Snake.Directions> snakesDirections = new HashMap<>();
        for (Integer id : snakesData.keySet()) {
            snakesDirections.put(id, snakesData.get(id).first.getDirection());
        }
        return snakesDirections;
    }

    public HashMap<Integer, ArrayList<Pair<Integer, Integer>>> getSnakesCoords() {
        HashMap<Integer, ArrayList<Pair<Integer, Integer>>> res = new HashMap<>();
        for (Integer id : snakesData.keySet()) {
            res.put(id, snakesData.get(id).first.getSnakeCoords(field));
        }
        return res;
    }

    public void addSnake(int id, int speed) {
        if (this.snakesData.size() < maxNum) {
            Pair<Cell, Snake.Directions> options = getStartSettings(id);
            Snake newSnake = new Snake(options.first, options.second, speed);
            OnlineSnakeMoveController newController = new OnlineSnakeMoveController(newSnake, field);
            this.snakesData.put(id, new Pair<>(newSnake, newController));
        }
    }



    protected Pair<Cell, Snake.Directions> getStartSettings(int snakeNum) {
        Pair<Cell, Snake.Directions> result;
        StartLocation startLocation = StartLocation.values()[snakeNum];
        Snake.Directions startDirection = null;
        Cell startCell = null;
        switch (startLocation) {
            case topLeft:
                startDirection = Snake.Directions.right;
                startCell = field.getCell(0, 0);
                break;
            case topRight:
                startDirection = Snake.Directions.left;
                startCell = field.getCell(0, field.getAmountColumns() - 1);
                break;
            case bottomLeft:
                startDirection = Snake.Directions.right;
                startCell = field.getCell(field.getAmountRows() - 1, 0);
                break;
            case bottomRight:
                startDirection = Snake.Directions.left;
                startCell = field.getCell(field.getAmountRows() - 1, field.getAmountColumns() - 1);
                break;
        }
        result = new Pair<>(startCell, startDirection);
        return result;
    }


    protected boolean isCollisionWithSnakes(int snakeNum) {
        for (Map.Entry<Integer, Pair<Snake, OnlineSnakeMoveController>> snakeData : this.snakesData.entrySet()) {
            if (snakeNum != snakeData.getKey()) {
                boolean collision = !snakesData.get(snakeNum).second.isNotCollisionWithSnake(snakeData.getValue().first);
                if (collision) {
                    snakesData.get(snakeNum).second.setCollision();
                    return true;
                }
            }
        }
        return false;
    }

    public HashMap<Integer, Cell> move() {
        HashMap<Integer, Cell> tails = new HashMap<>();
        for (Map.Entry<Integer, Pair<Snake, OnlineSnakeMoveController>> snakeData : this.snakesData.entrySet()) {
            if (!isAlive(snakeData.getKey()) || isCollisionWithSnakes(snakeData.getKey())) {
                tails.put(snakeData.getKey(), null);
            } else {
                tails.put(snakeData.getKey(), snakeData.getValue().second.move());
            }
        }
        return tails;
    }

    public ArrayList<Integer> getIdsOfAliveSnakes() {
        ArrayList<Integer> ids = new ArrayList<>();
        for (Map.Entry<Integer, Pair<Snake, OnlineSnakeMoveController>> snakeData : this.snakesData.entrySet()) {
            if (isAlive(snakeData.getKey())) {
                ids.add(snakeData.getKey());
            }
        }
        return ids;
    }



    public Snake getSnake(int number) {
        if (!this.snakesData.containsKey(number)) {
            return null;
        } else {
            return this.snakesData.get(number).first;
        }
    }

    public boolean isAlive(int id) {
        if (!this.snakesData.containsKey(id)) {
            return false;
        }
        return !this.snakesData.get(id).second.isCollision();
    }

    public void killSnake(int id) {
        if (this.snakesData.containsKey(id)) {
            this.snakesData.get(id).second.setCollision();
        }
    }

    protected int sumLengths() {
        int sum = 0;
        for (Map.Entry<Integer, Pair<Snake, OnlineSnakeMoveController>> snakeData : this.snakesData.entrySet()) {
            sum += snakeData.getValue().first.getBody().size();
        }
        return sum;
    }

    public void reset() {
        for (Map.Entry<Integer, Pair<Snake, OnlineSnakeMoveController>> snakeData : this.snakesData.entrySet()) {
            Pair<Cell, Snake.Directions> options = getStartSettings(snakeData.getKey());
            snakeData.getValue().first.reset(options.first, options.second);
            snakeData.getValue().second.restart(options.second);
        }
    }

    public int getNumOfActive() {
        int res = 0;
        for (Map.Entry<Integer, Pair<Snake, OnlineSnakeMoveController>> snakeData : this.snakesData.entrySet()) {
            if (isAlive(snakeData.getKey())) {
                res++;
            }
        }
        return res;
    }

    public Cell[] getTakenCells() {
        Cell[] cells = new Cell[sumLengths()];
        int index = 0;
        for (Map.Entry<Integer, Pair<Snake, OnlineSnakeMoveController>> snakeData : this.snakesData.entrySet()) {
            for (Cell cell : snakeData.getValue().first.getBody()) {
                cells[index++] = cell;
            }
        }
        return cells;
    }

    public int getSnakeNumByHead(Cell cell) {
        for (Map.Entry<Integer, Pair<Snake, OnlineSnakeMoveController>> snakeData : this.snakesData.entrySet()) {
            if (snakeData.getValue().first.getHead().equals(cell)) {
                return snakeData.getKey();
            }
        }
        return -1;
    }

    public void setSpeed(int speed) {
        for (Map.Entry<Integer, Pair<Snake, OnlineSnakeMoveController>> snakeData : this.snakesData.entrySet()) {
            snakeData.getValue().first.setSpeed(speed);
        }
    }

    public int getSnakesNum() {
        return this.snakesData.size();
    }

    public OnlineSnakeMoveController getController(int id) {
        return snakesData.get(id).second;
    }

}
