package server;

import loader.Loader;
import model.Group;
import model.Student;
import model.Students;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static loader.Loader.DATE_FORMATTER;

public class Server {
    private static ServerData serverData;
    public static ArrayList<MyServerThread> threads;
    private static boolean listening = true;

    public static void main(String[] args) {
        serverData = new ServerData(Loader.loadStudents());
        if (serverData.getStudents().getStudents().isEmpty()){
            serverData.getStudents().getStudents().addAll(getTestModel());
        }

        ServerSocket serverSocket = null;
        threads = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (listening){
            try {
                MyServerThread myServerThread = new MyServerThread(serverSocket.accept(), serverData);
                threads.add(myServerThread);
                myServerThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Objects.requireNonNull(serverSocket).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Student> getTestModel() {
        try {
            return Arrays.asList(new Student( new Group(1, "department1"), "name1", DATE_FORMATTER.parse("03/06/1999"))
                    , new Student( new Group(1, "department1"), "name2", DATE_FORMATTER.parse("03/06/1998"))
                    , new Student( new Group(1, "department1"), "name3", DATE_FORMATTER.parse("03/06/1997")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}

class MyServerThread extends Thread{
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ServerData data;
    private boolean listening = true;


    public MyServerThread(Socket clientSocket, ServerData data) {
        this.clientSocket = clientSocket;
        this.data = data;
        System.out.println(clientSocket.getRemoteSocketAddress().toString());
    }

    @Override
    public void run() {
        super.run();
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.writeObject(data.getStudents());
            while (listening) {
                in = new ObjectInputStream(clientSocket.getInputStream());
                final Students students = (Students) in.readObject();
                Server.threads.forEach(t -> {
                    t.send(students);
                });
                Loader.saveStudent(students);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void send(Students students){
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            data.setStudents(students);
            out.writeObject(data.getStudents());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
