package net.astechdesign.clients.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import net.astechdesign.clients.model.contact.Contact;
import net.astechdesign.clients.model.contact.ContactRepo;
import net.astechdesign.clients.model.order.Order;
import net.astechdesign.clients.model.todo.Todo;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {

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
        newOrderDialog.setVisible(false);
        selectContact(0);
    }

    public Contact selectContact(int id) {
        try {
            contact = ContactRepo.get(id);
            name.setText(contact.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contact;
    }

    @FXML
    private AnchorPane newOrderDialog;

    @FXML
    private ListView newOrderList;

    public void setContactDetailsPane(VBox contactDetailsPane) {
        detailsTabPane.getChildren().add(contactDetailsPane);
    }
}
