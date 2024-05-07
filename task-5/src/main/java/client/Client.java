package client;

import events.Event;
import service.Observable;
import service.Observer;
import socketGameMessage.SocketGameMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Client extends Observable implements Observer, Runnable {

    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    private final int clientId;

    public Client(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.clientId = handShake();
        System.out.println("Successfully connected to " + host + ":" + port + ", clientId: " + clientId);
    }

    public int getClientId() {
        return clientId;
    }

    protected int handShake() throws IOException {
        return Integer.parseInt(in.readLine());
    }

    protected Event parseMessage(String message) throws NumberFormatException {
        SocketGameMessage socketGameMessage = new SocketGameMessage(message);
        return socketGameMessage.getEvent();
    }

    public Event readMessage() throws IOException {
        return parseMessage(in.readLine());
    }

    public void sendMessage(Event event) throws IOException {
        SocketGameMessage msg = new SocketGameMessage(event);
        out.println(msg.toString());
    }

    @Override
    public void actionPerformed(Event event) {
        try {
            this.sendMessage(event);
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    @Override
    public void run() {
        while (true) {
            Event msg = null;
            try {
                msg = readMessage();
                notify(msg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
