package field;

import cell.Cell;
import painter.Painter;

import java.awt.*;

public class FieldPainter implements Painter {

    final Field field;
    final Color mainColor;

    final Color secondaryColor;

    public FieldPainter(Field field, Color mainColor, Color secondaryColor) {
        this.field = field;
        this.mainColor = mainColor;
        this.secondaryColor = secondaryColor;
    }

    public FieldPainter(Field field) {
        this(field, new Color(134, 255, 128, 182),
                new Color(87, 155, 103));
    }

    @Override
    public void paint(Graphics g) {
        for (int i = 0; i < field.getAmountRows(); i++) {
            for (int j = 0; j < field.getAmountColumns(); j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(mainColor);
                } else {
                    g.setColor(secondaryColor);
                }
                Cell cell = field.getCell(i, j);
                g.fillRect(cell.leftX, cell.topY, cell.size, cell.size);
            }
        }
    }
}
