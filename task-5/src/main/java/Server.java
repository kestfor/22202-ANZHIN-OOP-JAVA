import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final ServerSocket serverSocket;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void listen() throws IOException {
        Socket client = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        String msgFromClient = in.readLine();
        System.out.println(msgFromClient);
        out.println("answer from server");
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(80);
            while (true) {
                server.listen();
            }
        } catch (IOException exception) {
            System.err.println(exception.getLocalizedMessage());
        }
    }
}
