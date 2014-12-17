package net.astechdesign.clients.gui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.ResourceBundle;

public class TodosController extends Controller implements Initializable {

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
        update();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
        dateCol.setCellValueFactory(new PropertyValueFactory<Todo, LocalDate>("date"));
        contactCol.setCellValueFactory(new PropertyValueFactory<Todo, String>("name"));
        notesCol.setCellValueFactory(new PropertyValueFactory<Todo, String>("notes"));

        dateCol.setStyle(Css.COL_FONT_SIZE);
        contactCol.setStyle(Css.COL_FONT_SIZE);
        notesCol.setStyle(Css.COL_FONT_SIZE);

        todosTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                int id = ((Todo) newValue).getContactId();
                if (id == -1) return;
                setContact(id);
                mainController.showDetails();
            }
        });

    }

    @Override
    public void update() {
        try {
            todosTable.getSelectionModel().clearSelection();
            todosTable.setItems(FXCollections.observableArrayList(TodoRepo.todos()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
