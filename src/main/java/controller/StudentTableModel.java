package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import model.Student;

import java.util.Date;

public class StudentTableModel {
    private SimpleStringProperty name;
    private SimpleIntegerProperty groupNumber;
    private SimpleStringProperty groupDepartment;
    private SimpleObjectProperty<Date> date;
    private Student holder;

    public StudentTableModel(Student holder) {
        this.holder = holder;
        this.name = new SimpleStringProperty(holder.getName());
        this.groupNumber = new SimpleIntegerProperty(holder.getGroup().getNumber());
        this.groupDepartment = new SimpleStringProperty(holder.getGroup().getDepartment());
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

    public int getGroupNumber() {
        return groupNumber.get();
    }

    public SimpleIntegerProperty groupNumberProperty() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber.set(groupNumber);
        holder.getGroup().setNumber(groupNumber);
    }

    public String getGroupDepartment() {
        return groupDepartment.get();
    }

    public SimpleStringProperty groupDepartmentProperty() {
        return groupDepartment;
    }

    public void setGroupDepartment(String groupDepartment) {
        this.groupDepartment.set(groupDepartment);
        holder.getGroup().setDepartment(groupDepartment);
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
