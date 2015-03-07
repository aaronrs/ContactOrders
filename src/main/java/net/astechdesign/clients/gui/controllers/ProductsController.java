package net.astechdesign.clients.gui.controllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import net.astechdesign.clients.model.product.Product;
import net.astechdesign.clients.model.product.ProductRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ProductsController extends Controller implements Initializable {

    @FXML
    private Button productsBtn;

    @FXML
    private TextField code;

    @FXML
    private TextField description;

    @FXML
    private TextField price;

    @FXML
    private Button addProductBtn;

    @FXML
    private TableView productsTable;

    @FXML
    private TableColumn codeCol;

    @FXML
    private TableColumn descriptionCol;

    @FXML
    private TableColumn delCol;

    @FXML
    private TableColumn<Product, String> priceCol;

    @FXML
    private AnchorPane confirmDialog;

    @FXML
    private Label dialogCode;

    @FXML
    private Label dialogDescription;

    public Product deleteProduct;

    @FXML
    void editCol(TableColumn.CellEditEvent<Product, String> event) {
        Product product = (Product) event.getTableView().getItems().get(event.getTablePosition().getRow());
        String id = ((TableColumn) event.getSource()).getId();
        if (id.equals("codeCol")) {
            product.setCode(event.getNewValue());
        } else if (id.equals("descriptionCol")) {
            product.setName(event.getNewValue());
        } else if (id.equals("priceCol")) {
            product.setPrice(event.getNewValue());
        }
        try {
            ProductRepo.updateCode(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        update();
    }

    @FXML
    void addProduct(ActionEvent event) {
        String codeVal = code.getText().toUpperCase();
        String name = description.getText().toUpperCase();
        String priceVal = price.getText();
        Product product = new Product(0, codeVal, name, priceVal);
        try {
            ProductRepo.save(product);
            update();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        code.clear();
        description.clear();
        price.clear();
    }

    @FXML
    void confirmDelete(ActionEvent event) {
        try {
            ProductRepo.delete(deleteProduct.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        update();
        confirmDialog.setVisible(false);
    }

    @FXML
    void cancelDelete(ActionEvent event) {
        confirmDialog.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirmDialog.setVisible(false);
        update();

        codeCol.setCellValueFactory(new PropertyValueFactory<Product, String>("code"));
        codeCol.setCellFactory(TextFieldTableCell.forTableColumn());

        descriptionCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());

        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        priceCol.setCellFactory(column -> {
//            return new TableCell<Product, String>() {
//                @Override
//                protected void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//                    if (item != null) {
//                        setText("£" + item);
//                    }
//                }
//            };
//        });

        delCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Product, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        delCol.setCellFactory(new Callback<TableColumn<Product, Boolean>, TableCell<Product, Boolean>>() {
            @Override
            public TableCell<Product, Boolean> call(TableColumn<Product, Boolean> param) {
                return new DeleteButtonCell();
            }
        });

        codeCol.setStyle(Css.COL_FONT_SIZE);
        descriptionCol.setStyle(Css.COL_FONT_SIZE);
        priceCol.setStyle(Css.COL_FONT_SIZE);
    }

    @Override
    public void update() {
        try {
            List<Product> productList = ProductRepo.products();
            productsTable.setItems(FXCollections.observableArrayList(productList));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private class DeleteButtonCell extends TableCell<Product, Boolean> {
        final Button cellButton = new Button("X");

        DeleteButtonCell() {
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    Product product = (Product)productsTable.getItems().get(getIndex());
                    deleteProduct = product;
                    dialogCode.setText(product.getCode());
                    dialogDescription.setText(product.getName());
                    confirmDialog.setVisible(true);
                }
            });
        }

        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }
    }
}
