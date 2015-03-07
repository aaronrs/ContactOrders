package net.astechdesign.clients.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import net.astechdesign.clients.gui.fxml.FmxlWrapper;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends Controller implements Initializable {

    @FXML
    private Label title;

    @FXML
    private BorderPane contentPane;

    @FXML
    private Button deliveriesBtn;

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
    public AnchorPane deliveriesPane;

    public ProductsController productsController;
    public ContactsController contactsController;
    public ContactDetailsController contactDetailsController;
    public TodosController todosController;
    public DeliveriesController deliveriesController;
    public DetailsController detailsController;
    public OrderDetailsController orderDetailsController;
    public TodoDetailsController todoDetailsController;

    @FXML
    void selectDeliveries(ActionEvent event) {
        title.setText("DELIVERIES");
        showAll();
        deliveriesBtn.setDisable(true);
        deliveriesController.update();
        contentPane.setCenter(deliveriesPane);
    }

    @FXML
    void selectTodos(ActionEvent event) {
        title.setText("TO DOs");
        showAll();
        todosBtn.setDisable(true);
        todosController.update();
        contentPane.setCenter(todosPane);
    }

    @FXML
    void selectContacts(ActionEvent event) {
        title.setText("CONTACTS");
        showAll();
        contactsBtn.setDisable(true);
        contactsController.update();
        contentPane.setCenter(contactsPane);
    }

    @FXML
    void selectProducts(ActionEvent event) {
        title.setText("PRODUCTS");
        showAll();
        productsBtn.setDisable(true);
        productsController.update();
        contentPane.setCenter(productsPane);
    }

    public void showDetails() {
        title.setText(getContact().getName());
        showAll();
        contactDetailsController.update();
        todoDetailsController.update();
        orderDetailsController.update();
        contentPane.setCenter(detailsPane);
    }

    @FXML
    void exitAction(ActionEvent event) {
        System.exit(0);
    }

    private void showAll() {
        deliveriesBtn.setDisable(false);
        todosBtn.setDisable(false);
        contactsBtn.setDisable(false);
        productsBtn.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ClassLoader classLoader = MainController.class.getClassLoader();

        FmxlWrapper<DeliveriesController, AnchorPane> deliveriesWrapper = new FmxlWrapper<>(classLoader, "fxml/deliveries.fxml", this);
        FmxlWrapper<ProductsController, AnchorPane> productsWrapper = new FmxlWrapper<>(classLoader, "fxml/products.fxml", this);
        FmxlWrapper<ContactsController, AnchorPane> contactsWrapper = new FmxlWrapper<>(classLoader, "fxml/contacts.fxml", this);
        FmxlWrapper<TodosController, AnchorPane> todosWrapper = new FmxlWrapper<>(classLoader, "fxml/todos.fxml", this);
        FmxlWrapper<DetailsController, AnchorPane> detailsWrapper = new FmxlWrapper<>(classLoader, "fxml/details.fxml", this);
        FmxlWrapper<ContactDetailsController, AnchorPane> contactDetailsWrapper = new FmxlWrapper<>(classLoader, "fxml/contactDetails.fxml", this);
        FmxlWrapper<TodoDetailsController, AnchorPane> todosDetailsWrapper = new FmxlWrapper<>(classLoader, "fxml/todoDetails.fxml", this);
        FmxlWrapper<OrderDetailsController, AnchorPane> ordersDetailsWrapper = new FmxlWrapper<>(classLoader, "fxml/orderDetails.fxml", this);

        deliveriesPane = deliveriesWrapper.getPane();
        productsPane = productsWrapper.getPane();
        contactsPane = contactsWrapper.getPane();
        todosPane = todosWrapper.getPane();
        detailsPane = detailsWrapper.getPane();

        deliveriesController = deliveriesWrapper.getController();
        productsController = productsWrapper.getController();
        contactsController = contactsWrapper.getController();
        todosController = todosWrapper.getController();
        detailsController = detailsWrapper.getController();
        contactDetailsController = contactDetailsWrapper.getController();
        orderDetailsController = ordersDetailsWrapper.getController();
        todoDetailsController = todosDetailsWrapper.getController();

        detailsController.setContactDetailsPane(contactDetailsWrapper.getPane());
        detailsController.setTodosDetailsPane(todosDetailsWrapper.getPane());
        detailsController.setOrdersDetailsPane(ordersDetailsWrapper.getPane());

        selectDeliveries(null);
    }

    @Override
    public void update() {
    }
}
