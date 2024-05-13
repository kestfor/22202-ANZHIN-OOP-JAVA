package server;

import game.GameModel;
import fullGames.ServerSnakeGame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class GameServer {

    public static final int MAX_SIZE = 4;
    public static final int PORT = 8081;

    public static final PriorityQueue<Integer> AvailableIds = new PriorityQueue<Integer>(){{
        for (int i = 0; i < MAX_SIZE; i++) {
            add(i);
        }
    }};

    public static final ArrayList<CommunicationHandler> communicationHandlers = new ArrayList<>();

    public void run() throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            ServerSnakeGame serverSnakeGame = new ServerSnakeGame();
            serverSnakeGame.attachServer(this);
            serverSnakeGame.run();

            while (true) {
                Socket newSocket = server.accept();
                try {

                    if (serverSnakeGame.gameModel.getGameState() != GameModel.GameState.init || communicationHandlers.size() == MAX_SIZE) {
                        newSocket.close();
                        continue;
                    }

                    CommunicationHandler newHandler = new CommunicationHandler(newSocket);
                    communicationHandlers.add(newHandler);

                    newHandler.addObserver(serverSnakeGame);
                    serverSnakeGame.serverGameController.addObserver(newHandler);

                    Thread thread = new Thread(newHandler);
                    thread.start();

                } catch (IOException  e) {
                    newSocket.close();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        GameServer gameServer = new GameServer();
        gameServer.run();
    }
}
