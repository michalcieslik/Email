package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
    public static void main(String[] args) throws IOException {
        Socket socket;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(6666);
        }catch (IOException e){
            System.out.println("Server error");
        }
        while (true){
            try {
                socket = serverSocket.accept();
                System.out.println("Polaczenie z " + socket.getRemoteSocketAddress());
                ClientHandling clientHandling = new ClientHandling(socket);
                clientHandling.start();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("Blad z polaczeniem");
            }
        }

    }
}
