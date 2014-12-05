package net.astechdesign.clients.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import net.astechdesign.clients.gui.fxml.FmxlWrapper;
import net.astechdesign.clients.model.contact.Contact;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {

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
    public AnchorPane todosPane;
    public AnchorPane detailsPane;

    public ProductsController productsController;
    public ContactDetailsController contactDetailsController;
    public TodosController todosController;
    public DetailsController detailsController;

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
        ClassLoader classLoader = MainController.class.getClassLoader();

        FmxlWrapper<ProductsController, AnchorPane> productsWrapper = new FmxlWrapper<>(classLoader, "fxml/products.fxml", this);
        FmxlWrapper<ContactsController, AnchorPane> contactsWrapper = new FmxlWrapper<>(classLoader, "fxml/contacts.fxml", this);
        FmxlWrapper<TodosController, AnchorPane> todosWrapper = new FmxlWrapper<>(classLoader, "fxml/todos.fxml", this);
        FmxlWrapper<DetailsController, AnchorPane> detailsWrapper = new FmxlWrapper<>(classLoader, "fxml/details.fxml", this);
        FmxlWrapper<ContactDetailsController, AnchorPane> contactDetailsWrapper = new FmxlWrapper<>(classLoader, "fxml/contactDetails.fxml", this);
        FmxlWrapper<TodoDetailsController, AnchorPane> todosDetailsWrapper = new FmxlWrapper<>(classLoader, "fxml/todoDetails.fxml", this);
        FmxlWrapper<OrderDetailsController, AnchorPane> ordersDetailsWrapper = new FmxlWrapper<>(classLoader, "fxml/orderDetails.fxml", this);

        productsPane = productsWrapper.getPane();
        contactsPane = contactsWrapper.getPane();
        todosPane = todosWrapper.getPane();
        detailsPane = detailsWrapper.getPane();

        productsController = productsWrapper.getController();
        contactDetailsController = contactDetailsWrapper.getController();
        todosController = todosWrapper.getController();
        detailsController = detailsWrapper.getController();

        detailsController.setContactDetailsPane(contactDetailsWrapper.getPane());
        detailsController.setTodosDetailsPane(todosDetailsWrapper.getPane());
        detailsController.setOrdersDetailsPane(ordersDetailsWrapper.getPane());
        contactDetailsController.setTitleName(detailsController.getNameLabel());

        showTodos();
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

    public void newContact() {
        showDetails(-1);
    }
}
