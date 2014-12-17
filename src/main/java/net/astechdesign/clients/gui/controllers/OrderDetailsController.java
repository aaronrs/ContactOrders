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
import java.util.ResourceBundle;

public class OrderDetailsController extends Controller implements Initializable {

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

    @FXML
    private Button deleteOrderBtn;

    private Order order;

    @FXML
    void addProduct(ActionEvent event) {
        if (productName.getValue() == null) return;

        Label filler = new Label();
        filler.setPrefWidth(80);

        TextField product = new TextField(productName.getValue().getName());
        product.setPrefWidth(330);

        TextField amountField = new TextField(amount.getText());
        amountField.setPrefWidth(55);

        DatePicker date = new DatePicker(deliveryDate.getValue());
        date.setPrefWidth(120);
        date.setPrefWidth(120);

        Button deleteBtn = new Button("DEL");

        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.getChildren().addAll(filler, product, amountField, date, deleteBtn);
        orderProducts.getChildren().add(2, hBox);

        deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                orderProducts.getChildren().removeAll(hBox);
            }
        });
    }

    @FXML
    void saveOrder(ActionEvent event) {
        int first = 0;
        ObservableList<Node> orders = orderProducts.getChildren();
        for (Node node : orders) {
            if (first > 1 && first < orders.size() - 1) {
                ObservableList<Node> children = ((HBox) node).getChildren();
                String product = ((TextField) children.get(1)).getText();
                int amount1 = Integer.parseInt(((TextField) children.get(2)).getText());
                LocalDate deliveryDate1 = ((DatePicker) children.get(3)).getValue();
                Order order = new Order(-1, getContact(), product, deliveryDate1, amount1, LocalDate.now());
                try {
                    OrderRepo.save(order);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            first++;
        }
        update();
        newOrderDialog.setVisible(false);
    }

    @FXML
    private VBox orderProducts;

    @FXML
    private AnchorPane confirmDialog;

    @FXML
    private Label dialogCode;

    @FXML
    private Label dialogDescription;

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn dateCol;

    @FXML
    private TableColumn productCol;

    @FXML
    private TableColumn amountCol;

    @FXML
    private TableColumn deliveryDateCol;

    private ObservableList<Product> productList;
    private ObservableList<Order> orderList;

    @FXML
    void newOrder(ActionEvent e) {
        newOrderDialog.setVisible(true);
    }

    @FXML
    void deleteOrder(ActionEvent event) {
        dialogCode.setText(order.getDeliveryDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
        dialogDescription.setText(order.getProduct());
        confirmDialog.setVisible(true);
    }

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
        deleteOrderBtn.setVisible(false);
        try {
            productList = FXCollections.observableArrayList(ProductRepo.products());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dateCol.setCellValueFactory(new PropertyValueFactory<Order, LocalDate>("createDate"));
        productCol.setCellValueFactory(new PropertyValueFactory<Order, String>("product"));
        amountCol.setCellValueFactory(new PropertyValueFactory<Order, Integer>("amount"));
        deliveryDateCol.setCellValueFactory(new PropertyValueFactory<Order, LocalDate>("deliveryDate"));

        dateCol.setStyle(Css.COL_FONT_SIZE);
        productCol.setStyle(Css.COL_FONT_SIZE);
        amountCol.setStyle(Css.COL_FONT_SIZE);
        deliveryDateCol.setStyle(Css.COL_FONT_SIZE);

        orderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (orderTable.getSelectionModel().getSelectedItem() != null) {
                this.order = newValue;
                deleteOrderBtn.setVisible(true);
            }
        });

        productName.setItems(productList);
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
        confirmDialog.setVisible(false);
        newOrderDialog.setVisible(false);
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
