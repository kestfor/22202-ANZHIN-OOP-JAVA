import javax.swing.*;
import java.awt.*;

public class Field {
    private final Cell[][] field;
    private final int cellSize;
    private final int columns;
    private final int rows;
    private final int leftX;
    private final int rightX;
    private final int bottomY;
    private final int topY;

    public Field(int leftX, int topY, int columns, int rows, int cellSize) {
        this.cellSize = cellSize;
        this.columns = columns;
        this.rows = rows;
        this.leftX = leftX;
        this.rightX = leftX + columns * cellSize;
        this.topY = topY;
        this.bottomY = topY + rows * cellSize;

        this.field = new Cell[rows][columns];
        initializeField();

    }

    private void initializeField() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                field[i][j] = new Cell(leftX + j * cellSize, topY + i * cellSize, cellSize, (i + j) % 2 == 0 ? Color.GREEN : Color.LIGHT_GRAY);
            }
        }
    }

    private void paintField(Graphics g) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                field[i][j].paintComponent(g);
            }
        }
    }

    public void draw(Graphics g) {
        paintField(g);
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
        return bottomY;
    }

    public int getLeftX() {
        return leftX;
    }

    public int getRightX() {
        return rightX;
    }

    public int getAmountColumns() {
        return columns;
    }

}
