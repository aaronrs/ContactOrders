package net.astechdesign.clients.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ContactsController implements Initializable {

    @FXML
    private BorderPane contentPane;

    @FXML
    private AnchorPane todosPane;

    @FXML
    private AnchorPane contactsPane;

    @FXML
    private AnchorPane detailsPane;

    @FXML
    private Button todosBtn;

    @FXML
    private Button contactsBtn;

    @FXML
    private Button productsBtn;

    public AnchorPane productPane;

    @FXML
    void selectTodos(ActionEvent event) {
        hideAll();
        todosPane.setVisible(true);
        todosBtn.setDisable(true);
    }

    @FXML
    void selectContacts(ActionEvent event) {
        hideAll();
        contactsPane.setVisible(true);
        contactsBtn.setDisable(true);
    }

    @FXML
    void selectProducts(ActionEvent event) {
        hideAll();
        productsBtn.setDisable(true);
    }

    @FXML
    void exitAction(ActionEvent event) {
        System.exit(0);
    }

    private void hideAll() {
        todosPane.setVisible(false);
        contactsPane.setVisible(false);
        detailsPane.setVisible(false);
        todosBtn.setDisable(false);
        contactsBtn.setDisable(false);
        productsBtn.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
