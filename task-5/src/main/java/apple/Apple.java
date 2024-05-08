package apple;

import cell.Cell;
import field.Field;
import utils.Pair;

public class Apple {

    public Cell cell;

    public Apple(Cell cell) {
        setCell(cell);
    }

    public void setCell(Cell cell) {
        this.cell = cell.copy();
    }

    public Pair<Integer, Integer> getCoords(Field field) {
        int leftX = field.getLeftX();
        int topY = field.getTopY();
        int size = cell.size;
        return new Pair<>((cell.leftX - leftX) / size, (cell.topY - topY) / size);
    }

    public Cell getCell() {
        return cell;
    }
}
