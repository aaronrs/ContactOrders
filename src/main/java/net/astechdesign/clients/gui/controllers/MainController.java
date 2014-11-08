package net.astechdesign.clients.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class MainController {

    @FXML
    private AnchorPane todosPane;

    @FXML
    private AnchorPane contactsPane;

    @FXML
    private AnchorPane detailsPane;

    @FXML
    private AnchorPane productsPane;

    @FXML
    private Button todosBtn;

    @FXML
    private Button contactsBtn;

    @FXML
    private Button productsBtn;

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
        productsPane.setVisible(true);
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
        productsPane.setVisible(false);
        todosBtn.setDisable(false);
        contactsBtn.setDisable(false);
        productsBtn.setDisable(false);
    }
}
