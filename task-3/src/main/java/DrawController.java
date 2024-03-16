import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DrawController extends JPanel{

    public List<Drawable> objects;
    private int score;

    private int bestScore;

    private final int height;

    private final int width;

    boolean isOver = false;
    boolean isPaused = false;

    DrawController(int width, int height, List<Drawable> objects) {
        this.height = height;
        this.width = width;
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.objects = objects;
    }

    public void setScore(int newScore) {
        score = newScore;
        if (newScore > bestScore) {
            bestScore = newScore;
        }
    }

    public int getBestScore() {
        return bestScore;
    }

    public int getScore() {
        return score;
    }

    public void setObjects(List<Drawable> objects) {
        this.objects = objects;
    }

    void reset() {
        this.isOver = false;
        this.isPaused = false;
        this.score = 0;
        this.objects = Collections.emptyList();
    }

    private void paintScoreBoard(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font("Times New Roman", Font.BOLD, 20));
        g.drawString("Score " + score, 0, 20);
        g.drawString("Best score: " + bestScore, 0, 40);
    }

    private void paintDrawableObjects(Graphics g) {
        for (Drawable object : objects) {
            object.draw(g);
        }
    }

    private void paintGameState(Graphics g) {
        g.setFont(new Font("Times New Roman", Font.BOLD, 60));

        if (isOver) {
            g.setColor(Color.RED);
            g.drawString("GAME OVER", width / 2 - 170, 100);
            g.setFont(new Font("Times New Roman", Font.BOLD, 30));
            g.setColor(Color.LIGHT_GRAY);
            g.drawString("Press enter to play again", width / 2 - 140, 140);
            return;
        }

        if (isPaused) {
            g.setColor(Color.YELLOW);
            g.drawString("PAUSED", width / 2 - 125, 100);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintDrawableObjects(g);
        paintScoreBoard(g);
        paintGameState(g);
    }
}
