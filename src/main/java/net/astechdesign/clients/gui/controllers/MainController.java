package net.astechdesign.clients.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import net.astechdesign.clients.model.contact.Contact;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private BorderPane contentPane;

    @FXML
    private Button todosBtn;

    @FXML
    private Button contactsBtn;

    @FXML
    private Button productsBtn;

    public AnchorPane productsPane;
    public AnchorPane contactsPane;
    public AnchorPane detailsPane;
    public VBox todosPane;

    public DetailsController detailsController;
    public ContactDetailsController contactDetailsController;
    public ProductsController productsController;

    @FXML
    void selectTodos(ActionEvent event) {
        showAll();
        showTodos();
        todosBtn.setDisable(true);
    }

    @FXML
    void selectContacts(ActionEvent event) {
        showAll();
        showContacts();
        contactsBtn.setDisable(true);
    }

    @FXML
    void selectProducts(ActionEvent event) {
        showAll();
        showProducts();
        productsBtn.setDisable(true);
    }

    @FXML
    void exitAction(ActionEvent event) {
        System.exit(0);
    }

    private void showAll() {
        todosBtn.setDisable(false);
        contactsBtn.setDisable(false);
        productsBtn.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void showProducts() {
        productsController.updateProductTable();
        contentPane.setCenter(productsPane);
    }

    public void showContacts() {
        contentPane.setCenter(contactsPane);
    }

    public void showDetails(int id) {
        showAll();
        Contact contact = detailsController.selectContact(id);
        contactDetailsController.setContact(contact);
        contentPane.setCenter(detailsPane);
    }

    public void showTodos() {
        contentPane.setCenter(todosPane);
    }
}
