package game;

import apple.ApplePainter;
import field.FieldPainter;
import snake.SnakePainter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class GamePanel extends JPanel {

    private final FieldPainter fieldPainter;
    private final SnakePainter snakePainter;
    private final ApplePainter applePainter;
    private final GameModel gameModel;

    private final String appleImagePath = "./apple40px.png";

    public GamePanel(GameModel gameModel) {
        ApplePainter resApplePainter;
        this.gameModel = gameModel;
        this.fieldPainter = new FieldPainter(gameModel.getField());
        this.snakePainter = new SnakePainter(gameModel.getSnake());

        URL resource = getClass().getClassLoader().getResource(appleImagePath);
        if (resource != null) {
            try {
                resApplePainter = new ApplePainter(gameModel.getApple(), new File(resource.toURI()));
            } catch (URISyntaxException e) {
                resApplePainter = new ApplePainter(gameModel.getApple());
            }
        } else {
            resApplePainter = new ApplePainter(gameModel.getApple());
        }

        this.applePainter = resApplePainter;
        this.setBackground(new Color(107, 107, 107));
    }

    private void paintGamePause(Graphics g) {
        g.setColor(new Color(255, 174, 0));
        g.setFont(new Font("Times New Roman", Font.BOLD, 100));

        int x = this.getWidth() / 2;
        int y = 100;
        g.drawString("Pause", x - 100, y);
    }

    private void paintScore(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Times New Roman", Font.BOLD, 50));
        int x = 0;
        int y = 50;

        g.drawString("Score: " + gameModel.getScore(), x, y);
    }

    private void paintGameInit(Graphics g) {
        g.setColor(new Color(204, 141, 141));
        g.setFont(new Font("Times New Roman", Font.BOLD, 50));

        int x = this.getWidth() / 2;
        int y = 100;
        g.drawString("press enter to start", x - 190, y);
    }

    private void paintGameOver(Graphics g) {
        g.setColor(new Color(180, 16, 16));
        g.setFont(new Font("Times New Roman", Font.BOLD, 100));

        int x = this.getWidth() / 2;
        int y = 100;
        g.drawString("Game Over", x - 240, y);
    }

    private void paintGameWin(Graphics g) {
        g.setColor(new Color(211, 132, 132));
        g.setFont(new Font("Times New Roman", Font.BOLD, 50));

        int x = this.getWidth() / 2;
        int y = 100;
        g.drawString("Congratulations, you won the game!", x - 350, y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        fieldPainter.paint(g);
        applePainter.paint(g);
        snakePainter.paint(g);

        paintScore(g);


        switch (gameModel.getGameState()) {
            case pause:
                paintGamePause(g);
                break;
            case over:
                paintGameOver(g);
                break;
            case init:
                paintGameInit(g);
                break;
            case win:
                paintGameWin(g);
                break;
        }

    }

}
