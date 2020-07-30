package loader;

import model.Students;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class Loader {
    public static final String DATE_PATTERN = "dd/MM/yyyy";
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_PATTERN);
    public static final DateTimeFormatter D = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static void saveStudent(Students students){
        if (students != null){
            try {
                FileOutputStream out = new FileOutputStream(new File("save"));
                ObjectOutputStream outStream = new ObjectOutputStream(out);
                outStream.writeObject(students);
                outStream.flush();
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Students loadStudents (){
        Students students = null;
        try {
            FileInputStream in = new FileInputStream(new File("save"));
            ObjectInputStream inStream = new ObjectInputStream(in);
            students = (Students) inStream.readObject();
            inStream.close();
        } catch (IOException | ClassNotFoundException e) {
            students = new Students();
        }
        return students;
    }
}
