package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Students implements Serializable {
 private final ArrayList<Student> students;

    public Students() {
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public void remove(Student student){
        students.remove(student);
    }

    public void remove(int index){
        students.remove(index);
    }

    public Student get(int index){
        return students.get(index);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

}
