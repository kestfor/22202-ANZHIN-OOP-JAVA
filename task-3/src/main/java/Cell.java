import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {
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

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(borderColor);
        g.drawRect(leftX, topY, size, size);
        g.setColor(mainColor);
        g.fillRect(leftX, topY, size, size);
    }

}
