package model;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable {

    private Group group;
    private String name;
    private Date date;

    public Student(Group group, String name, Date date) {
        this.group = group;
        this.name = name;
        this.date = date;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Student{" +
                "group=" + group +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
