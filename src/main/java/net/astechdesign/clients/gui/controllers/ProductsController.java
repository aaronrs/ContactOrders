package net.astechdesign.clients.gui.controllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import net.astechdesign.clients.model.product.Product;
import net.astechdesign.clients.model.product.ProductRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ProductsController implements Initializable {

    private ObservableList<Product> productsList;

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
    private TableColumn priceCol;

    @FXML
    void editCodeCol(TableColumn.CellEditEvent<Product, String> event) {
        Product product = (Product) event.getTableView().getItems().get(event.getTablePosition().getRow());
        product.setCode(event.getNewValue());
        try {
            ProductRepo.save(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateProductTable();
    }

    @FXML
    void addProduct(ActionEvent event) {
        Product product = new Product(0, code.getText(), description.getText(), "");
        try {
            ProductRepo.save(product);
            updateProductTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        code.clear();
        description.clear();
        price.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateProductTable();

        codeCol.setCellValueFactory(new PropertyValueFactory<Product, String>("code"));
        codeCol.setCellFactory(TextFieldTableCell.forTableColumn());

        descriptionCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));

        delCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, Boolean>, ObservableValue<Boolean>>() {
            @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Product, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        delCol.setCellFactory(new Callback<TableColumn<Product, Boolean>, TableCell<Product, Boolean>>() {
            @Override
            public TableCell<Product, Boolean> call(TableColumn<Product, Boolean> param) {
                return new ButtonCell(productsList);
            }
        });
    }

    private void updateProductTable() {
        try {
            List<Product> productList = ProductRepo.products();
            productsTable.setItems(FXCollections.observableArrayList(productList));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private class ButtonCell extends TableCell<Product, Boolean> {
        final Button cellButton = new Button("X");

        ButtonCell(final ObservableList<Product> data) {
            cellButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Product product = (Product)productsTable.getItems().get(getIndex());
                    try {
                        ProductRepo.delete(product.getId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    updateProductTable();
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
