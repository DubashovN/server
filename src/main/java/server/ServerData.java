package server;

import model.Students;

public class ServerData {

    private Students students;

    public ServerData(Students students) {
        this.students = students;
    }

    public Students getStudents() {
        return students;
    }

    public void setStudents(Students students) {
        this.students = students;
    }
}
