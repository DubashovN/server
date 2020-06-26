package model;

import java.io.Serializable;

public class Group implements Serializable {
    private Integer number;
    private String department;

    public Group(Integer number, String department) {
        this.number = number;
        this.department = department;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Group{" +
                "number=" + number +
                ", department='" + department + '\'' +
                '}';
    }
}
