package net.astechdesign.clients.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import net.astechdesign.clients.model.order.Order;
import net.astechdesign.clients.model.order.OrderRepo;
import net.astechdesign.clients.model.product.Product;
import net.astechdesign.clients.model.product.ProductRepo;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class OrderDetailsController extends Controller implements Initializable {

    @FXML
    private TableColumn<Order, LocalDate> dateCol;

    @FXML
    private TableColumn<Order, LocalDate> deliveryDateCol;

    @FXML
    private TableColumn<Order, String> productCol;

    @FXML
    private TableColumn<Order, Integer> priceCol;

    @FXML
    private TableColumn<Order, Integer> amountCol;

    private void initMain(){
        deleteOrderBtn.setVisible(false);
        dateCol.setCellValueFactory(new PropertyValueFactory("createDate"));
        deliveryDateCol.setCellValueFactory(new PropertyValueFactory("deliveryDate"));
        productCol.setCellValueFactory(new PropertyValueFactory("product"));
        priceCol.setCellValueFactory(new PropertyValueFactory("price"));
        amountCol.setCellValueFactory(new PropertyValueFactory("amount"));

        dateCol.setStyle(Css.COL_FONT_SIZE);
        deliveryDateCol.setStyle(Css.COL_FONT_SIZE);
        productCol.setStyle(Css.COL_FONT_SIZE);
        priceCol.setStyle(Css.COL_FONT_SIZE);
        amountCol.setStyle(Css.COL_FONT_SIZE);

        dateCol.setCellFactory(new DateCellFactory<>());
        deliveryDateCol.setCellFactory(new DateCellFactory<>());

        orderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (orderTable.getSelectionModel().getSelectedItem() != null) {
                this.order = newValue;
                deleteOrderBtn.setVisible(true);
            }
        });
    }

    @FXML
    void newOrder(ActionEvent e) {
        resetNewOrderFields();
        newOrderDialog.setVisible(true);
    }

    private void resetNewOrderFields() {
        productCode.clear();
        deliveryDate.setValue(LocalDate.now());
        productName.getSelectionModel().clearSelection();
        amount.setText("1");
        updateProductList("");
    }

    @FXML
    void deleteOrder(ActionEvent event) {
        dialogCode.setText(order.getDeliveryDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        dialogDescription.setText(order.getProduct());
        confirmDialog.setVisible(true);
    }

    private List<Product> productList;

    @FXML
    private AnchorPane newOrderDialog;

    @FXML
    private TextField productCode;

    @FXML
    private ComboBox<Product> productName;

    @FXML
    private TextField amount;

    @FXML
    private DatePicker deliveryDate;

    private void initOrder() {
        try {
            productList = ProductRepo.products();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        productName.setItems(FXCollections.observableArrayList(productList));
        productName.setConverter(new StringConverter<Product>() {
            @Override
            public String toString(Product object) {
                return object.getName();
            }

            @Override
            public Product fromString(String string) {
                return null;
            }
        });
        deliveryDate.setValue(LocalDate.now());
        newOrderDialog.setVisible(false);
    }

    @FXML
    void addProduct(ActionEvent event) {
        if (productName.getValue() == null) return;

        Label filler = new Label();
        filler.setPrefWidth(80);

        DatePicker deliveryDate = new DatePicker(this.deliveryDate.getValue());
        deliveryDate.setPrefWidth(160);

        TextField product = new TextField(productName.getValue().getName());
        product.setPrefWidth(330);

        TextField priceField = new TextField(productName.getValue().getPrice());
        priceField.setPrefWidth(75);

        TextField amountField = new TextField(amount.getText());
        amountField.setPrefWidth(55);

        Button deleteBtn = new Button("DEL");
        deleteBtn.getStyleClass().add("button1");

        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.getChildren().addAll(deliveryDate, product, priceField, amountField, deleteBtn);
        orderProducts.getChildren().add(hBox);

        deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                orderProducts.getChildren().removeAll(hBox);
            }
        });

        resetNewOrderFields();
    }

    @FXML
    void saveOrder(ActionEvent event) {
        ObservableList<Node> orders = orderProducts.getChildren();
        for (Node node : orders) {
            ObservableList<Node> children = ((HBox) node).getChildren();
            LocalDate deliveryDate1 = ((DatePicker) children.get(0)).getValue();
            String product = ((TextField) children.get(1)).getText();
            String price = ((TextField) children.get(2)).getText();
            int amount1 = Integer.parseInt(((TextField) children.get(3)).getText());
            Order order = new Order(-1, getContact(), product, deliveryDate1, price, amount1, LocalDate.now());
            try {
                OrderRepo.save(order);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        update();
        mainController.todoDetailsController.update();
        newOrderDialog.setVisible(false);
    }

    @FXML
    void cancelOrder(ActionEvent event) {
        orderProducts.getChildren().clear();
        update();
        newOrderDialog.setVisible(false);
    }

    @FXML
    void searchProducts(KeyEvent event) {
        String text = productCode.getText();
        if (event != null && !event.getCharacter().equals("\b")) {
            text += event.getCharacter();
        }
        updateProductList(text);
        productName.show();
    }

    private void updateProductList(String text) {
        try {
            productList = ProductRepo.find(text);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        productName.setItems(FXCollections.observableArrayList(productList));
    }

    private Order order;

    @FXML
    private VBox orderProducts;

    @FXML
    private AnchorPane confirmDialog;

    @FXML
    private Label dialogCode;

    @FXML
    private Label dialogDescription;

    @FXML
    private Button deleteOrderBtn;

    @FXML
    private TableView<Order> orderTable;

    private ObservableList<Order> orderList;

    @FXML
    void confirmDelete(ActionEvent e) {
        try {
            OrderRepo.deleteOrder(order);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        confirmDialog.setVisible(false);
        mainController.selectContacts(null);
    }

    @FXML
    void cancelDelete(ActionEvent e) {
        confirmDialog.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMain();
        initOrder();
        confirmDialog.setVisible(false);
    }

    @Override
    public void update() {
        try {
            orderList = FXCollections.observableArrayList(OrderRepo.orders(getContactId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        orderTable.setItems(orderList);
    }
}
