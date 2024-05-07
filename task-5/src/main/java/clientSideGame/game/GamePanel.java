package clientSideGame.game;

import clientSideGame.apple.ApplePainter;
import clientSideGame.field.FieldPainter;
import clientSideGame.snake.Snake;
import clientSideGame.snake.SnakePainter;
import clientSideGame.snake.SnakesManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {

    private final FieldPainter fieldPainter;
    //private final ArrayList<SnakePainter> snakePainters;
    private final ApplePainter applePainter;
    private final GameModel gameModel;
    private final Timer timer;
    private final String appleImagePath = "./apple40px.png";

    public GamePanel(GameModel gameModel) {
        this.timer = new Timer(100, this);
        ApplePainter resApplePainter;
        this.gameModel = gameModel;
        this.fieldPainter = new FieldPainter(gameModel.getField());
        //this.snakePainters = new ArrayList<>();
//        for (int i = 0; i < gameModel.getSnakesManager().getSnakesNum(); i++) {
//            this.snakePainters.add(new SnakePainter(gameModel.getSnakesManager().getSnake(i), SnakePainter.colors.get(SnakesManager.StartLocation.values()[i])));
//        }

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
        this.timer.start();
    }

    private void paintGamePause(Graphics g) {
        g.setColor(new Color(255, 174, 0));
        g.setFont(new Font("Times New Roman", Font.BOLD, 100));

        int x = this.getWidth() / 2;
        int y = 100;
        g.drawString("Pause", x - 100, y);
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
        int playerId = gameModel.getSnakesManager().getIdsOfAliveSnakes().get(0);
        g.drawString("Player " + (playerId + 1) + " won", x - 150, y);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        fieldPainter.paint(g);
        applePainter.paint(g);

        ArrayList<SnakePainter> snakePainters = new ArrayList<>();
        for (int i = 0; i < SnakesManager.maxNum; i++) {
            Snake snake = gameModel.getSnakesManager().getSnake(i);
            if (snake != null) {
                snakePainters.add(new SnakePainter(snake, SnakePainter.colors.get(SnakesManager.StartLocation.values()[i])));
            }
        }
        for (SnakePainter snakePainter : snakePainters) {
            snakePainter.paint(g);
        }

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

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
