package net.astechdesign.clients.gui.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import net.astechdesign.clients.model.todo.Todo;
import net.astechdesign.clients.model.todo.TodoRepo;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class TodoDetailsController extends Controller implements Initializable {

    @FXML
    private TextArea todoNotes;

    @FXML
    private DatePicker todoDate;

    @FXML
    private TableView todosTable;

    @FXML
    private TableColumn dateCol;

    @FXML
    private TableColumn notesCol;

    @FXML
    void addTodo(ActionEvent event) {
        Todo todo = new Todo(0, getContactId(), todoDate.getValue(), todoNotes.getText(), getContact().getName());
        try {
            TodoRepo.save(todo);
            todoNotes.clear();
            todoDate.setValue(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        update();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
        dateCol.setCellValueFactory(new PropertyValueFactory<Todo, LocalDate>("date"));
        notesCol.setCellValueFactory(new PropertyValueFactory<Todo, String>("notes"));

        dateCol.setStyle(Css.COL_FONT_SIZE);
        notesCol.setStyle(Css.COL_FONT_SIZE);
    }

    @Override
    public void update() {
        List<Todo> list = null;
        try {
            list = TodoRepo.todos();
            todosTable.getSelectionModel().clearSelection();
            todosTable.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
