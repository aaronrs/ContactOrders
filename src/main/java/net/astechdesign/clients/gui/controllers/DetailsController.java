package net.astechdesign.clients.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import net.astechdesign.clients.model.contact.Contact;
import net.astechdesign.clients.model.contact.ContactRepo;
import net.astechdesign.clients.model.order.Order;
import net.astechdesign.clients.model.todo.Todo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DetailsController extends Controller implements Initializable {

    private Contact contact;

    @FXML
    private Label name;

    @FXML
    private AnchorPane detailsTabPane;

    @FXML
    private AnchorPane todosTabPane;

    @FXML
    private AnchorPane ordersTabPane;

    @FXML
    private TextField todoNotes;

    @FXML
    private DatePicker todoDate;

    @FXML
    private TableView<Todo> todoTable;

    @FXML
    private TableView<Order> orderTable;

    @FXML
    void addTodo(ActionEvent event) {
    }

    @FXML
    void addProduct(ActionEvent event) {
    }

    @FXML
    void saveOrder(ActionEvent event) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectContact(-1);
    }

    public Contact selectContact(int id) {
        if (id == -1) {
            contact = new Contact(-1, "", "", "", null);
        } else {
            try {
                contact = ContactRepo.get(id);
                name.setText(contact.getName());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return contact;
    }

    public void setContactDetailsPane(AnchorPane pane) {
        detailsTabPane.getChildren().add(pane);
    }

    public void setTodosDetailsPane(AnchorPane pane) {
        todosTabPane.getChildren().add(pane);
    }

    public void setOrdersDetailsPane(AnchorPane pane) {
        ordersTabPane.getChildren().add(pane);
    }

    public Label getNameLabel() {
        return name;
    }

    @Override
    public void update() {}
}
