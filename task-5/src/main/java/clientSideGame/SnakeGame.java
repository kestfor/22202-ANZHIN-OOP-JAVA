package clientSideGame;

import client.Client;
import events.Event;
import events.NewFieldSizeEvent;
import events.NewSnakeSpeedEvent;
import clientSideGame.game.GameController;
import clientSideGame.game.GameModel;
import clientSideGame.game.GamePanel;
import clientSideGame.game.Settings;
import clientSideGame.menu.FieldMenuSection;
import clientSideGame.menu.GameMenuBar;
import clientSideGame.menu.MenuSection;
import clientSideGame.menu.SnakeSpeedMenuSection;
import service.Observer;
import socketGameMessage.events.*;
import utils.Pair;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SnakeGame extends JFrame implements Observer {

    public Settings settings;
    public GameModel gameModel;
    public Client client;
    public GamePanel gamePanel;

    public GameController gameController;

    public static void main(String[] args) {
        SnakeGame snakeGame = new SnakeGame();

        try {
            snakeGame.client = new Client("localhost", 8080);
        } catch (IOException exception) {
            System.out.println(exception.getLocalizedMessage());
            return;
        }

        Thread clientThread = new Thread(snakeGame.client);
        clientThread.start();

        snakeGame.client.addObserver(snakeGame);
        snakeGame.setSize(new Dimension(1280, 720));
        snakeGame.setTitle("snake game");

        snakeGame.settings = new Settings(FieldMenuSection.sizes.get(FieldMenuSection.FieldSizes.standard), 40, 720, 1280, SnakeSpeedMenuSection.speed.get(SnakeSpeedMenuSection.SnakeSpeed.standard));
        snakeGame.gameModel = new GameModel(snakeGame.settings, snakeGame.client.getClientId());
        snakeGame.gamePanel = new GamePanel(snakeGame.gameModel);
        snakeGame.gameController = new GameController(snakeGame.gameModel, snakeGame.gamePanel, snakeGame.client);

        ArrayList<MenuSection> sections = new ArrayList<>(Arrays.asList(new FieldMenuSection("field", snakeGame.settings), new SnakeSpeedMenuSection("speed", snakeGame.settings)));
        GameMenuBar menuBar = new GameMenuBar(sections);

        snakeGame.getContentPane().add(snakeGame.gamePanel);
        snakeGame.setJMenuBar(menuBar);

        snakeGame.setVisible(true);
        snakeGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        snakeGame.addKeyListener(snakeGame.gameController);

        snakeGame.settings.addObserver(snakeGame);
        snakeGame.gameController.addObserver(snakeGame.client);
        snakeGame.client.actionPerformed(new NewAppleEvent(snakeGame.client.getClientId(), snakeGame.gameModel.getApplePosition()));
        snakeGame.client.actionPerformed(new NewPlayerEvent(snakeGame.client.getClientId()));
        snakeGame.gameController.run();
    }


    @Override
    public void actionPerformed(Event event) {
        if (event instanceof NewFieldSizeEvent) {
            this.settings.setSize(((NewFieldSizeEvent) event).getNewSize());
            this.gameModel.getField().resize(this.settings.getSize());
            this.gameController.restart();
        }
        if (event instanceof NewSnakeSpeedEvent) {
            this.settings.setSnakeSpeed(((NewSnakeSpeedEvent) event).getNewSpeed());
            this.gameModel.getSnakesManager().setSpeed(this.settings.getSnakeSpeed());
            this.gameController.restart();
        }

        handleSocketEvents(event);
    }

    public void handleSocketEvents(Event event) {

        if (event instanceof NewAppleEvent) {
            Pair<Integer, Integer> position = ((NewAppleEvent) event).getPosition();
            gameModel.getApple().setCell(gameModel.getField().getCell(position.first, position.second));
        }

        if (event instanceof NewPlayerEvent) {
            if (this.client.getClientId() == ((NewPlayerEvent) event).getClientId()) {
                return;
            }
            if (gameModel.getGameState() == GameModel.GameState.init) {
                this.gameModel.getSnakesManager().addSnake(((NewPlayerEvent) event).getClientId(), settings.getSnakeSpeed());
            }
        }

        if (event instanceof ExitPlayerEvent) {
            this.gameModel.getSnakesManager().removeSnake(((ExitPlayerEvent) event).getClientId());
        }

        if (event instanceof DirectionEvent) {
            DirectionEvent directionEvent = (DirectionEvent) event;
            this.gameModel.getSnakesManager().getController(directionEvent.getSnakeNum()).setDirection(directionEvent.getDirection());
        }

        if (event instanceof RestartEvent) {
            gameController.restart();
        }

        if (event instanceof StartEvent) {
            gameController.startGame();
        }

        if (event instanceof PauseEvent) {
            switch (gameModel.getGameState()) {
                case pause:
                    gameController.unPauseGame();
                    break;
                case active:
                    gameController.pauseGame();
                    break;
                default:
                    break;
            }
        }
    }

}
