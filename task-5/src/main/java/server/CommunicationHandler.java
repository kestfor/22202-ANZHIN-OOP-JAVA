package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class CommunicationHandler extends Thread {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private int clientId;

    public CommunicationHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        start();
        handShake();
    }

    protected void handShake() {
        clientId = GameServer.AvailableIds.remove();
        System.out.println("Handshake sent, id: " + clientId);
        this.out.println(clientId);
    }

    protected void removeClient() {
        GameServer.communicationHandlers.remove(this);
        GameServer.AvailableIds.add(clientId);
        System.out.println("client disconnected");
    }


    @Override
    public void run() {
        String data;
        while (true) {
            try {
                data = in.readLine();
                if (data == null) {
                    removeClient();
                    break;
                }
                for (CommunicationHandler handler : GameServer.communicationHandlers) {
                    handler.sendMessage(data);
                }
            } catch (IOException e) {
                if (e instanceof SocketException) {
                    removeClient();
                    break;
                } else {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

}
