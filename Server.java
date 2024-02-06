import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(1234);
            List<String> inboxTaylan = Collections.synchronizedList(new ArrayList<>());
            List<String> inboxCevher = Collections.synchronizedList(new ArrayList<>());
            List<String> sentTaylan = Collections.synchronizedList(new ArrayList<>());
            List<String> sentCevher = Collections.synchronizedList(new ArrayList<>());
            List<Object> subjectlist = Collections.synchronizedList(new ArrayList<>());


            while (true) {
                socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket, inboxCevher, inboxTaylan, sentCevher, sentTaylan, subjectlist);

                // Yeni bir Thread başlatarak handler'ın run metodu çağrılacak
                new Thread(handler).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

