package apple;

import painter.Painter;
import cell.Cell;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ApplePainter implements Painter {

    final Apple apple;
    final BufferedImage image;

    public ApplePainter(Apple apple, File image) {
        this.apple = apple;
        BufferedImage bufferedImage;
        try {
            bufferedImage =  ImageIO.read(image);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            bufferedImage = null;
        }
        this.image = bufferedImage;
    }

    public ApplePainter(Apple apple) {
        this.apple = apple;
        this.image = null;
    }

    @Override
    public void paint(Graphics g) {
        Cell cell = apple.getCell();
        if (image != null) {
            g.drawImage(image, cell.leftX , cell.topY, null);
        } else {
            g.setColor(Color.red);
            g.fillOval(cell.leftX + cell.size / 4, cell.topY + cell.size / 4, cell.size / 2, cell.size / 2);
        }
    }
}
