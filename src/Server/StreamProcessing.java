package Server;

import Comunication.Message;

import java.io.*;
import java.net.Socket;

public class StreamProcessing {
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private InputStream inputStream;
    private OutputStream outputStream;
    public StreamProcessing(Socket socket) throws IOException {
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
        outputStream.flush();
    }

    public Message readData() throws IOException, ClassNotFoundException {
        objectInputStream = new ObjectInputStream(inputStream);
        return (Message) objectInputStream.readObject();
    }

    public void sendData(Message message) throws IOException {
        objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(message);
    }
}
