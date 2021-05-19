package Client;

import Comunication.Message;
import Server.StreamProcessing;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket = null;
    private final int port;
    private final String address;
    private boolean isWorking = false;
    private InterpreterClient interpreterClient;
    private Account account;
    private StreamProcessing streamProcessing;
    private Message messageTosend;

    public Client(int port, String address) {
        this.port = port;
        this.address = address;
        this.interpreterClient = new InterpreterClient();
        this.account = new Account(null, null);
    }

    public void openConnection() throws IOException {

        this.socket = new Socket(address, port);
        streamProcessing = new StreamProcessing(this.socket);
        this.isWorking = true;


    }

    public void closeConnection() throws IOException {
        this.socket.close();
        this.isWorking = false;
    }

    public void send(Message messageTosend) throws IOException {
        if (isWorking) {
            this.messageTosend = messageTosend;
            streamProcessing.sendData(messageTosend);
        }
    }

    public boolean read() throws Exception {
        if (isWorking) {
            Message receivedMessage = streamProcessing.readData();
            return interpreterClient.Do(receivedMessage, account, messageTosend);
        }
        return false;
    }

    public Account getAccount() {
        return account;
    }

}