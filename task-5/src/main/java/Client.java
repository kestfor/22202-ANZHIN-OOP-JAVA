import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;

    public Client(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void send(String msg) {
        out.println(msg);
    }

    public String getResponse() throws IOException {
        return in.readLine();
    }

    public void close() throws IOException {
        if (!socket.isClosed()) {
            in.close();
            out.close();
            socket.close();
        }
    }

    public static void main(String[] args) {
        try {
            ArrayList<Client> clients = new ArrayList<>();
            int size = 10;
            for (int i = 0; i < size; i++) {
                clients.add(new Client("localhost", 80));
            }

            for (Client client : clients) {
                client.send("hello");
            }

            for (Client client : clients) {
                String res = client.getResponse();
                System.out.println(res);
            }

            for (Client client : clients) {
                client.close();
            }

        } catch (IOException exception) {
            System.err.println(exception.getLocalizedMessage());
        }
    }
}
