package controller;

import interfaces.ReadObjectListener;
import interfaces.WriteObjectListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Student;
import model.Students;

import javax.swing.table.TableModel;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    public TableView<StudentTableModel> table;
    public TableColumn<StudentTableModel, String> nameColumn;
    public TableColumn<StudentTableModel, Integer> numberColumn;
    public TableColumn<StudentTableModel, String> departmentColumn;
    public TableColumn<StudentTableModel, Date> dateColumn;

    public TextField nameTextField;
    public TextField groupNumberTextField;
    public TextField groupDepartmentTextField;
    public DatePicker datePicker;
    public TextField search;

    private ObservableList<StudentTableModel> tableModel;
    private ObservableList<StudentTableModel> searchTableModel;
    private Students students;
    private ReadObjectListener readObjectListener;
    private WriteObjectListener writeObjectListener;

    private void bindCollections(){
        tableModel = FXCollections.observableArrayList();
        searchTableModel = FXCollections.observableArrayList();
        students.getStudents().forEach(e -> {
            tableModel.add(new StudentTableModel(e));
        });
        table.setItems(tableModel);
        table.toFront();
    }

    public Controller() {
        readObjectListener = (s -> {
           students = s;
           bindCollections();
           setUpNameColumn();
        });
    }

    private void setUpNameColumn(){
        nameColumn.setCellFactory(EditCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> {
            String value = event.getNewValue() != null ? event.getNewValue() : event.getOldValue();
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(value);
            table.refresh();
        });
    }

    public ReadObjectListener getReadObjectListener() {
        return readObjectListener;
    }

    public void setWriteObjectListener(WriteObjectListener writeObjectListener) {
        this.writeObjectListener = writeObjectListener;
    }

    public void onActionAdd(ActionEvent actionEvent) {
    }

    public void onActionDelete(ActionEvent actionEvent) {
    }

    public void onActionSave(ActionEvent actionEvent) {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
