package net.astechdesign.clients.gui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.astechdesign.clients.model.todo.Todo;
import net.astechdesign.clients.model.todo.TodoRepo;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DeliveriesController extends Controller implements Initializable {

    @FXML
    private TableView<Todo> deliveryTable;

    @FXML
    private TableColumn<Todo, LocalDate> dateCol;

    @FXML
    private TableColumn<Todo, String> contactCol;

    @FXML
    private TableColumn<Todo, String> notesCol;

    @FXML
    private TableColumn<Todo, String> telephoneCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        update();
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        notesCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
        telephoneCol.setCellValueFactory(new PropertyValueFactory<>("town"));

        dateCol.setCellFactory(new DateCellFactory<>());

        dateCol.setStyle(Css.COL_FONT_SIZE);
        contactCol.setStyle(Css.COL_FONT_SIZE);
        notesCol.setStyle(Css.COL_FONT_SIZE);

        deliveryTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Todo>() {
            @Override
            public void changed(ObservableValue<? extends Todo> observable, Todo oldValue, Todo newValue) {
                if (newValue != null && newValue.getContactId() != -1) {
                    setContact(newValue.getContactId());
                    mainController.showDetails();
                }
            }
        });
    }

    @Override
    public void update() {
        try {
            deliveryTable.getSelectionModel().clearSelection();
            deliveryTable.setItems(FXCollections.observableArrayList(TodoRepo.deliveries()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
