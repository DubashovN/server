package controller;

import loader.Loader;
import interfaces.ReadObjectListener;
import interfaces.WriteObjectListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import model.Group;
import model.Student;
import model.Students;

import java.net.URL;
import java.text.ParseException;
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
           setUpNumberColumn();
           setGroupDepartmentColumn();
           setDateColumn();
           setTableEditable();
           search.setOnAction(e -> {
               String regex = search.getText();
               if (!regex.trim().isEmpty()){
                   searchTableModel.clear();
                   tableModel.forEach(i -> {
                       if (i.getName().matches(regex) || i.getGroupDepartment().matches(regex)){
                           searchTableModel.add(i);
                       }
                   });
                   table.setItems(searchTableModel);
               } else {
                   table.setItems(tableModel);
               }
           });
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

    private void setUpNumberColumn(){
        numberColumn.setCellFactory(EditCell.forTableColumn(new MyIntegerConverter()));
        numberColumn.setOnEditCommit(e -> {
            final Integer value = e.getNewValue() != null ? e.getNewValue() : e.getOldValue();
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setGroupNumber(value);
            table.refresh();
        });
    }

    private void setGroupDepartmentColumn(){
        departmentColumn.setCellFactory(EditCell.forTableColumn());
        departmentColumn.setOnEditCommit(e ->{
            final String value = e.getNewValue() != null ? e.getNewValue() : e.getOldValue();
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setGroupDepartment(value);
            table.refresh();
        });
    }

    private void setDateColumn(){
        dateColumn.setCellFactory(EditCell.forTableColumn(new MyDateConverter(Loader.DATE_PATTERN)));
        dateColumn.setOnEditCommit(e -> {
            final Date value = e.getNewValue() != null ? e.getNewValue() : e.getOldValue();
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setDate(value);
            table.refresh();
        });
    }

    private void setTableEditable() {
        table.setEditable(true);
        table.getSelectionModel().cellSelectionEnabledProperty().set(true);
        table.setOnKeyPressed(event -> {
            if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
                editFocusedCell();
            } else if (event.getCode() == KeyCode.RIGHT
                    || event.getCode() == KeyCode.TAB) {
                table.getSelectionModel().selectNext();
                event.consume();
            } else if (event.getCode() == KeyCode.LEFT) {
                selectPrevious();
                event.consume();
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void editFocusedCell() {
        final TablePosition focusedCell = table
                .focusModelProperty().get().focusedCellProperty().get();
        table.edit(focusedCell.getRow(), focusedCell.getTableColumn());
    }

    @SuppressWarnings("unchecked")
    private void selectPrevious() {
        if (table.getSelectionModel().isCellSelectionEnabled()) {
            TablePosition<StudentTableModel, ?> pos = table.getFocusModel().getFocusedCell();
            if (pos.getColumn() - 1 >= 0) {
                table.getSelectionModel().select(pos.getRow(), getTableColumn(pos.getTableColumn()));
            } else if (pos.getRow() < table.getItems().size()) {
                table.getSelectionModel().select(pos.getRow() - 1, table.getVisibleLeafColumn(table.getVisibleLeafColumns().size() - 1));
            }
        } else {
            int focusIndex = table.getFocusModel().getFocusedIndex();
            if (focusIndex == -1) {
                table.getSelectionModel().select(table.getItems().size() - 1);
            } else if (focusIndex > 0) {
                table.getSelectionModel().select(focusIndex - 1);
            }
        }
    }

    private boolean numberIsValid(){
        if (!groupNumberTextField.getText().isEmpty()){
            try {
                Integer.valueOf(groupNumberTextField.getText());
                return true;
            }catch (NumberFormatException e){
                return false;
            }
        }
        return false;
    }

    private boolean dateIsValid(){
        if (datePicker.getValue().format(Loader.D).isEmpty()){
            try {
                String dateString = datePicker.getValue().format(Loader.D);
                Loader.DATE_FORMATTER.parse(dateString);
            }catch (ParseException e){
                return false;
            }
        }
        return false;
    }



    private TableColumn<StudentTableModel, ?> getTableColumn(
            final TableColumn<StudentTableModel, ?> column) {
        int columnIndex = table.getVisibleLeafIndex(column);
        int newColumnIndex = columnIndex + -1;
        return table.getVisibleLeafColumn(newColumnIndex);
    }

    public ReadObjectListener getReadObjectListener() {
        return readObjectListener;
    }

    public void setWriteObjectListener(WriteObjectListener writeObjectListener) {
        this.writeObjectListener = writeObjectListener;
    }

    public void onActionAdd(ActionEvent actionEvent) {

//        if (dateIsValid() && numberIsValid()){
            String name = nameTextField.getText();
            int groupNumber = Integer.parseInt(groupNumberTextField.getText());
            String groupDepartment = groupDepartmentTextField.getText();
            Date date = null;
            String dateString = datePicker.getValue().format(Loader.D);
            try {
                date = Loader.DATE_FORMATTER.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Student student = new Student(new Group(groupNumber, groupDepartment), name,  date);
            students.addStudent(student);
            tableModel.add(new StudentTableModel(student));
            writeObjectListener.write();
            System.out.println("123");
//        }
    }

    public void onActionDelete(ActionEvent actionEvent) {
    }

    public void onActionSave(ActionEvent actionEvent) {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
