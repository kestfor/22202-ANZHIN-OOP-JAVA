package fullGames;

import client.Client;
import events.Event;
import game.*;
import service.Observer;
import socketGameMessage.events.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ClientSnakeGame extends JFrame implements Observer, Runnable {

    public Settings settings;
    public GameModel gameModel;
    public Client client;
    public GamePanel gamePanel;

    public ClientGameController clientGameController;

    public ClientSnakeGame() throws IOException {
        setSize(new Dimension(1280, 720));
        setTitle("snake game");

        this.client = new Client("localhost", 8081);
        Thread clientThread = new Thread(client);
        clientThread.start();

        settings = new Settings();
        gameModel = new GameModel(settings);
        gamePanel = new GamePanel(gameModel);
        clientGameController = new ClientGameController(gameModel, gamePanel, client);
        getContentPane().add(gamePanel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addKeyListener(this.clientGameController);
        client.addObserver(this);
        settings.addObserver(this);
    }

    public void run() {
        clientGameController.notify(new NewPlayerEvent(client.getClientId()));
    }

    public static void main(String[] args) {
        try {
            ClientSnakeGame game = new ClientSnakeGame();
            game.run();
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }



    @Override
    public void actionPerformed(events.Event event) {
        handleSocketEvents(event);
    }

    public void handleSocketEvents(Event event) {

        if (event instanceof EndEvent) {
            clientGameController.handleEndEvent(((EndEvent) event).getClientId());
        }

        if (event instanceof NewAppleEvent) {
            clientGameController.handleAppleEvent(((NewAppleEvent) event).getPosition());
        }

        if (event instanceof TickUpdate) {
            clientGameController.updateModel((TickUpdate) event);
        }

        if (event instanceof NewPlayerEvent) {
            clientGameController.handleNewPlayerEvent((NewPlayerEvent) event);
        }

        if (event instanceof ExitPlayerEvent) {
            clientGameController.handleExitPlayerEvent((ExitPlayerEvent) event);
        }

        if (event instanceof RestartEvent) {
            clientGameController.handleRestartEvent();
        }

        if (event instanceof StartEvent) {
            clientGameController.handleStartEvent();
        }

        if (event instanceof PauseEvent) {
            clientGameController.handlePauseEvent();
        }
    }

}
