package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.PriorityQueue;

public class GameServer {

    public static final int MAX_SIZE = 4;
    public static final int PORT = 8080;

    public static final PriorityQueue<Integer> AvailableIds = new PriorityQueue<Integer>(){{
        for (int i = 0; i < MAX_SIZE; i++) {
            add(i);
        }
    }};

    public static final BlockingQueue<CommunicationHandler> communicationHandlers = new BlockingQueue<>(MAX_SIZE);

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                Socket newSocket = server.accept();
                try {
                    communicationHandlers.put(new CommunicationHandler(newSocket));
                } catch (IOException e) {
                    newSocket.close();
                }
            }
        }
    }
}
