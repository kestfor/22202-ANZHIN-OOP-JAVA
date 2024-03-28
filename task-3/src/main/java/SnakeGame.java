import events.Event;
import events.NewFieldSizeEvent;
import events.NewSnakeSpeedEvent;
import game.GameController;
import game.GameModel;
import game.GamePanel;
import game.Settings;
import menu.FieldMenuSection;
import menu.GameMenuBar;
import menu.MenuSection;
import menu.SnakeSpeedMenuSection;
import service.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class SnakeGame extends JFrame implements Observer {

    public Settings settings;
    public GameModel gameModel;

    public GamePanel gamePanel;

    public GameController gameController;

    public static void main(String[] args) {
        SnakeGame snakeGame = new SnakeGame();

        snakeGame.setSize(new Dimension(1280, 720));
        snakeGame.setTitle("snake game");

        snakeGame.settings = new Settings(FieldMenuSection.sizes.get(FieldMenuSection.FieldSizes.standard), 40, 720, 1280, SnakeSpeedMenuSection.speed.get(SnakeSpeedMenuSection.SnakeSpeed.standard));
        snakeGame.gameModel = new GameModel(snakeGame.settings);
        snakeGame.gamePanel = new GamePanel(snakeGame.gameModel);
        snakeGame.gameController = new GameController(snakeGame.gameModel, snakeGame.gamePanel);

        ArrayList<MenuSection> sections = new ArrayList<>(Arrays.asList(new FieldMenuSection("field", snakeGame.settings), new SnakeSpeedMenuSection("speed", snakeGame.settings)));
        GameMenuBar menuBar = new GameMenuBar(sections);

        snakeGame.getContentPane().add(snakeGame.gamePanel);
        snakeGame.setJMenuBar(menuBar);

        snakeGame.setVisible(true);
        snakeGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        snakeGame.addKeyListener(snakeGame.gameController);

        snakeGame.settings.addObserver(snakeGame);
        snakeGame.gameController.run();
    }


    @Override
    public void notify(Event event) {
        if (event instanceof NewFieldSizeEvent) {
            this.settings.setSize(((NewFieldSizeEvent) event).getNewSize());
            this.gameModel.getField().resize(this.settings.getSize());
            this.gameController.restart();
        }
        if (event instanceof NewSnakeSpeedEvent) {
            this.settings.setSnakeSpeed(((NewSnakeSpeedEvent) event).getNewSpeed());
            this.gameModel.getSnake().setSpeed(this.settings.getSnakeSpeed());
            this.gameController.restart();
        }

    }

}
