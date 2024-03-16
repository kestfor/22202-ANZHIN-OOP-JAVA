import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Apple implements Drawable {

    public Cell cell;

    final BufferedImage image;

    public Apple(Cell cell) {
        BufferedImage image;
        try {
            image =  ImageIO.read(new File("C:\\study\\oop\\java\\task-3\\src\\main\\resources\\apple40px.png"));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            image = null;
        }
        this.image = image;
        setCell(cell);
    }

    @Override
    public void draw(Graphics g) {

        if (image != null) {
            g.drawImage(image, cell.leftX , cell.topY, null);
            return;
        }

        g.setColor(cell.getMainColor());
        g.fillOval(cell.leftX + cell.size / 4 , cell.topY + cell.size / 4, cell.size / 2, cell.size / 2);
    }

    public void setCell(Cell cell) {
        Cell copy = cell.copy();
        if (copy.getMainColor() != Color.red) {
            copy.setMainColor(Color.red);
        }
        this.cell = copy;
    }

}
