package clientSideGame.field;

import clientSideGame.cell.Cell;
import clientSideGame.utils.Pair;

public class Field {
    private Cell[][] field;
    private final int cellSize;
    private int columns;
    private int rows;
    private int leftX;
    private int topY;


    public Field(int leftX, int topY, int rows, int columns, int cellSize) {
        this.cellSize = cellSize;
        this.columns = columns;
        this.rows = rows;
        this.leftX = leftX;
        this.topY = topY;

        this.field = new Cell[rows][columns];
        initializeField();

    }

    private void initializeField() {
        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < columns; j++) {
                field[i][j] = new Cell(leftX + j * cellSize, topY + i * cellSize, cellSize);
            }
        }
    }

    public void resize(Pair<Integer, Integer> newSize) {
         int oldHeight = cellSize * rows;
         int heightDiff = newSize.first * cellSize - oldHeight;
         this.rows = newSize.first;
         topY -= heightDiff / 2;

         int oldWidth = cellSize * columns;
         int widthDiff = newSize.second * cellSize - oldWidth;
         this.columns = newSize.second;
         leftX -= widthDiff / 2;
         this.field = new Cell[rows][columns];
         initializeField();
    }

    public Cell getCell(int rowNum, int columnNumber) {
        return field[rowNum][columnNumber];
    }

    public int getAmountRows() {
        return rows;
    }

    public int getTopY() {
        return topY;
    }

    public int getBottomY() {
        return topY + rows * cellSize;
    }

    public int getLeftX() {
        return leftX;
    }

    public int getRightX() {
        return leftX + columns * cellSize;
    }

    public int getAmountColumns() {
        return columns;
    }

    public Cell[][] getArray() {
        return field;
    }

}
