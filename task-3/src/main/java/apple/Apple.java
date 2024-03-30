package apple;

import cell.Cell;

public class Apple {

    public Cell cell;

    public Apple(Cell cell) {
        setCell(cell);
    }

    public void setCell(Cell cell) {
        this.cell = cell.copy();
    }

    public Cell getCell() {
        return cell;
    }
}
