import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel{

    Snake snake;
    Field field;

    private final int height;

    private final int width;

    boolean isOver = false;

    GamePanel(int width, int height, Snake snake, Field field) {
        this.height = height;
        this.width = width;
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.snake = snake;
        this.field = field;
    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        field.draw(g);
        snake.draw(g);

        if (isOver) {
            g.setFont(new Font("Times New Roman", Font.BOLD, 60));
            g.drawString("GAME OVER", width / 2 - 170, 100);
        }
    }
}
