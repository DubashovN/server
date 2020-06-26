package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import model.Student;

import java.util.Date;

public class StudentTableModel {
    private SimpleStringProperty name;
    private SimpleIntegerProperty number;
    private SimpleStringProperty department;
    private SimpleObjectProperty<Date> date;
    private Student holder;

    public StudentTableModel(Student holder) {
        this.holder = holder;
        this.name = new SimpleStringProperty(holder.getName());
        this.number = new SimpleIntegerProperty(holder.getGroup().getNumber());
        this.department = new SimpleStringProperty(holder.getGroup().getDepartment());
        this.date = new SimpleObjectProperty<>(holder.getDate());
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
        holder.setName(name);
    }

    public int getNumber() {
        return number.get();
    }

    public SimpleIntegerProperty numberProperty() {
        return number;
    }

    public void setNumber(int number) {
        this.number.set(number);
        holder.getGroup().setNumber(number);
    }

    public String getDepartment() {
        return department.get();
    }

    public SimpleStringProperty departmentProperty() {
        return department;
    }

    public void setDepartment(String department) {
        this.department.set(department);
        holder.getGroup().setDepartment(department);
    }

    public Date getDate() {
        return date.get();
    }

    public SimpleObjectProperty<Date> dateProperty() {
        return date;
    }

    public void setDate(Date date) {
        this.date.set(date);
        holder.setDate(date);
    }

    public Student getHolder() {
        return holder;
    }

    public void setHolder(Student holder) {
        this.holder = holder;
    }
}
