package client;

import events.Event;
import service.Observable;
import service.Observer;
import socketGameMessage.SocketGameMessage;
import socketGameMessage.events.SocketEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class Client extends Observable implements Observer, Runnable {

    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final int clientId;

    public Client(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        Integer clientId = handShake();
        if (clientId == null) {
            throw new SocketException("Connection refused");
        } else {
            this.clientId = clientId;
        }
    }

    public int getClientId() {
        return clientId;
    }

    protected Integer handShake() throws IOException {
        String res = in.readLine();
        if (res == null) {
            return null;
        }
        return Integer.parseInt(res);
    }

    protected SocketEvent parseMessage(String message) throws Exception {
        if (message == null) {
            throw new SocketException("Server was closed");
        }
        SocketGameMessage socketGameMessage = new SocketGameMessage(message);
        return socketGameMessage.getEvent();
    }

    public SocketEvent readMessage() throws Exception {
        return parseMessage(in.readLine());
    }

    public void sendMessage(SocketEvent event) throws IOException {
        SocketGameMessage msg = new SocketGameMessage(event);
        out.println(msg);
    }

    @Override
    public void actionPerformed(Event event) {
        if (!(event instanceof SocketEvent)) {
            throw new RuntimeException("Event is not a SocketEvent");
        }
        try {
            this.sendMessage((SocketEvent) event);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    @Override
    public void run() {
        while (true) {
            SocketEvent msg;
            try {
                msg = readMessage();
                notify(msg);
            } catch (Exception e) {
                if (e instanceof SocketException) {
                    System.err.println(e.getLocalizedMessage());
                    break;
                }
            }
        }
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
            System.exit(0);
        }
    }
}
