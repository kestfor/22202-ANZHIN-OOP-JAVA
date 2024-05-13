package socketGameMessage.events;

import snake.Snake;
import socketGameMessage.SocketGameMessage;
import utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class TickUpdate extends SocketEvent {
    protected final String coordsDel = ",";
    protected final HashMap<Integer, ArrayList<Pair<Integer, Integer>>> snakesPosition;
    protected final HashMap<Integer, Snake.Directions> snakesDirections;
    protected final Pair<Integer, Integer> applePosition;


    public TickUpdate(HashMap<Integer, ArrayList<Pair<Integer, Integer>>> snakesPosition, HashMap<Integer, Snake.Directions> snakesDirections, Pair<Integer, Integer> applePosition) {
        this.snakesPosition = snakesPosition;
        this.applePosition = applePosition;
        this.snakesDirections = snakesDirections;
    }

    public TickUpdate(String rawString) {
        String[] tokens = rawString.split(SocketEvent.delimiter);
        String[] coords = tokens[0].split(coordsDel);
        applePosition = new Pair<>(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
        snakesPosition = new HashMap<>();
        snakesDirections = new HashMap<>();

        for (int i = 1; i < tokens.length; i+=3) {
            int snakeId = Integer.parseInt(tokens[i]);
            Snake.Directions direction = Snake.Directions.values()[Integer.parseInt(tokens[i+1])];
            ArrayList<Pair<Integer, Integer>> snakeCoordsList = new ArrayList<>();
            String[] snakeCoords = tokens[i+2].split(coordsDel);
            for (int j = 0; j < snakeCoords.length; j+= 2) {
                snakeCoordsList.add(new Pair<>(Integer.parseInt(snakeCoords[j]), Integer.parseInt(snakeCoords[j+1])));
            }
            snakesDirections.put(snakeId, direction);
            snakesPosition.put(snakeId, snakeCoordsList);
        }
    }

    public Pair<Integer, Integer> getApplePosition() {
        return applePosition;
    }

    public HashMap<Integer, ArrayList<Pair<Integer, Integer>>> getSnakesPositions(){
        return snakesPosition;
    }

    protected String snakesCoordsToString(ArrayList<Pair<Integer, Integer>> snakeCoords) {
        StringBuilder res = new StringBuilder();
        for (Pair<Integer, Integer> coords : snakeCoords) {
            res.append(coords.first).append(coordsDel).append(coords.second).append(coordsDel);
        }
        res.deleteCharAt(res.length() - 1);
        return res.toString();
    }

    protected String appleCoordsToString(Pair<Integer, Integer> appleCoords) {
        return appleCoords.first + coordsDel + appleCoords.second;
    }

    protected String SnakesInfoToString(HashMap<Integer, ArrayList<Pair<Integer, Integer>>> snakesInfo) {
        StringBuilder res = new StringBuilder();
        for (Integer snakeId : snakesInfo.keySet()) {
            res.append(snakeId).append(delimiter).append(snakesDirections.get(snakeId).ordinal()).append(delimiter).append(snakesCoordsToString(snakesInfo.get(snakeId))).append(delimiter);
        }
        res.deleteCharAt(res.length() - 1);
        return res.toString();
    }

    public HashMap<Integer, Snake.Directions> getSnakesDirections() {
        return snakesDirections;
    }

    @Override
    public String toString() {
        return SocketGameMessage.types.tickUpdate.ordinal() + delimiter + appleCoordsToString(applePosition) + delimiter + SnakesInfoToString(snakesPosition);
    }
}
