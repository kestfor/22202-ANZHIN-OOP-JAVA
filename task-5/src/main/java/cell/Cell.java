package cell;

public class Cell {
    public final int leftX;
    public final int rightX;
    public final int topY;
    public final int bottomY;

    public final int size;

    public Cell(int x, int y, int size) {
        this.leftX = x;
        this.topY = y;
        this.size = size;
        this.rightX = leftX + size;
        this.bottomY = topY + size;
    }


    public Cell copy() {
        return new Cell(this.leftX, this.topY, this.size);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Cell) {
            Cell cell = (Cell) object;
            return this.topY == cell.topY && this.leftX == cell.leftX && this.size == cell.size;
        } else {
            return false;
        }
    }


}
