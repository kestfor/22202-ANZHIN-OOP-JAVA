package apple;

import cell.Cell;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

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
