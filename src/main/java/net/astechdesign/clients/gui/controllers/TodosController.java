package net.astechdesign.clients.gui.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.astechdesign.clients.model.todo.Todo;
import net.astechdesign.clients.model.todo.TodoRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class TodosController implements Initializable {

    @FXML
    private TextField todoNote;

    @FXML
    private DatePicker todoDate;

    @FXML
    private TableView<Todo> todosTable;

    @FXML
    private TableColumn dateCol;

    @FXML
    private TableColumn contactCol;

    @FXML
    private TableColumn notesCol;

    @FXML
    void addTodo(ActionEvent event) {
        new Todo(0, -1, todoDate.getValue(), todoNote.getText(), "");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dateCol.setCellValueFactory(new PropertyValueFactory<Todo, Date>("date"));
        contactCol.setCellValueFactory(new PropertyValueFactory<Todo, String>("name"));
        notesCol.setCellValueFactory(new PropertyValueFactory<Todo, String>("notes"));
        updateTodosTable();
    }
    public void updateTodosTable() {
        try {
            List<Todo> list = TodoRepo.todos();
            todosTable.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
