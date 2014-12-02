package net.astechdesign.clients.gui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.astechdesign.clients.model.todo.Todo;
import net.astechdesign.clients.model.todo.TodoRepo;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class TodosController implements Initializable {

    @FXML
    private TextArea todoNotes;

    @FXML
    private DatePicker todoDate;

    @FXML
    private TableView todosTable;

    @FXML
    private TableColumn dateCol;

    @FXML
    private TableColumn contactCol;

    @FXML
    private TableColumn notesCol;

    public MainController mainController;

    @FXML
    void addTodo(ActionEvent event) {
        Todo todo = new Todo(0, -1, todoDate.getValue(), todoNotes.getText(), "");
        try {
            TodoRepo.save(todo);
            todoNotes.clear();
            todoDate.setValue(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateTodosTable();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateTodosTable();
        dateCol.setCellValueFactory(new PropertyValueFactory<Todo, LocalDate>("date"));
        contactCol.setCellValueFactory(new PropertyValueFactory<Todo, String>("name"));
        notesCol.setCellValueFactory(new PropertyValueFactory<Todo, String>("notes"));

        todosTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                int id = ((Todo) newValue).getContactId();
                mainController.showDetails(id);
            }
        });

    }
    public void updateTodosTable() {
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
