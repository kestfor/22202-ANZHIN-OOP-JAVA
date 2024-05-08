package fullGames;

import events.Event;
import game.ServerGameController;
import game.GameModel;
import game.Settings;
import menu.FieldMenuSection;
import menu.SnakeSpeedMenuSection;
import server.GameServer;
import service.Observer;
import socketGameMessage.events.*;

public class ServerSnakeGame implements Observer, Runnable {

    public Settings settings;
    public GameModel gameModel;
    public GameServer server;

    public ServerGameController serverGameController;

    public ServerSnakeGame() {
        settings = new Settings();
        gameModel = new GameModel(settings);
        serverGameController = new ServerGameController(gameModel);
        settings.addObserver(this);
    }

    public void run() {
        if (server == null) {
            throw new RuntimeException("there is not attached server");
        }
    }

    public void attachServer(GameServer gameServer) {
        this.server = gameServer;
    }


    @Override
    public void actionPerformed(Event event) {
        if (event instanceof SocketEvent) {
            handleSocketEvents(event);
        }
    }

    public void handleSocketEvents(Event event) {

        if (event instanceof NewPlayerEvent) {
            serverGameController.handleNewPlayerEvent((NewPlayerEvent) event);
        }

        if (event instanceof ExitPlayerEvent) {
            serverGameController.handleExitPlayerEvent((ExitPlayerEvent) event);
        }

        if (event instanceof DirectionEvent) {
            serverGameController.handleDirectionEvent((DirectionEvent) event);
        }

        if (event instanceof RestartEvent) {
           serverGameController.handleRestartEvent((RestartEvent) event);
        }

        if (event instanceof StartEvent) {
            serverGameController.handleStartEvent((StartEvent) event);
        }

        if (event instanceof PauseEvent) {
           serverGameController.handlePauseEvent((PauseEvent) event);
        }
    }

}
