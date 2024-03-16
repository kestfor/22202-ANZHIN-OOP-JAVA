import java.awt.*;

public class Cell implements Drawable{
    public final int leftX;
    public final int rightX;
    public final int topY;
    public final int bottomY;

    public final int size;

    private Color borderColor;

    private Color mainColor;

    Cell(int x, int y, int size, Color mainColor, Color borderColor) {
        this.leftX = x;
        this.topY = y;
        this.size = size;
        this.rightX = leftX + size;
        this.bottomY = topY + size;
        this.borderColor = borderColor;
        this.mainColor = mainColor;
    }

    void setBorderColor(Color newColor) {
        this.borderColor = newColor;
    }

    Color getBorderColor() {
        return borderColor;
    }

    Cell(int x, int y, int size, Color mainColor) {
        this(x, y, size, mainColor, Color.BLACK);
    }

    Cell(int x, int y, int size) {
        this(x, y, size, Color.GREEN);
    }

    void setMainColor(Color color) {
        mainColor = color;
    }

    Color getMainColor() {
        return mainColor;
    }

    Cell copy() {
        return new Cell(this.leftX, this.topY, this.size, this.mainColor, this.borderColor);
    }

    boolean equals(Cell cell) {
        return this.topY == cell.topY && this.leftX == cell.leftX && this.size == cell.size;
    }

    @Override
    public void draw(Graphics g) {
//        g.setColor(borderColor);
//        g.drawRect(leftX, topY, size, size);
        g.setColor(mainColor);
        g.fillRect(leftX, topY, size, size);
    }

}
