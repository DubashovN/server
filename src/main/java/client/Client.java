package client;

import interfaces.ReadObjectListener;
import interfaces.WriteObjectListener;
import model.Students;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private Socket socket;
    private Students students;
    private WriteObjectListener writeObjectListener;
    private ReadObjectListener readObjectListener;
    private boolean listening = true;
    private final String HOST_NAME = "localhost";
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public void connect() {
        try {
            socket = new Socket(HOST_NAME, 4444);
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeObjectListener = () -> {
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(students);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        try {
            in = new  ObjectInputStream(socket.getInputStream());
            students = (Students) in.readObject();
            readObjectListener.postRead(students);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            while (listening){
                try {
                    in = new ObjectInputStream(socket.getInputStream());
                    students = (Students) in.readObject();
                    readObjectListener.postRead(students);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void disconnect(){
        listening = false;
    }


    public WriteObjectListener getWriteObjectListener() {
        return writeObjectListener;
    }

    public void setReadObjectListener(ReadObjectListener readObjectListener) {
        this.readObjectListener = readObjectListener;
    }
}
