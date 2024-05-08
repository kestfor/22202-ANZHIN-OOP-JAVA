package server;

import events.Event;
import service.Observable;
import service.Observer;
import socketGameMessage.SocketGameMessage;
import socketGameMessage.events.ExitPlayerEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class CommunicationHandler extends Observable implements Runnable, Observer {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private int clientId;

    public CommunicationHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
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
        notify(new ExitPlayerEvent(clientId));
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
                SocketGameMessage msg = new SocketGameMessage(data);
                System.out.println("received: " + msg);
                try {
                    notify(msg.getEvent());
                } catch (Exception e) {
                    System.err.println(e.getLocalizedMessage());
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
        try {
            socket.close();
            this.finalize();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message) {
        out.println(message);
        System.out.println("sent message: " + message);
    }


    @Override
    public void actionPerformed(Event event) {
        sendMessage(event.toString());
    }
}
