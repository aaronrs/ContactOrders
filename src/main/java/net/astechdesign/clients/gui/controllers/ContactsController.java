package net.astechdesign.clients.gui.controllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import net.astechdesign.clients.model.contact.Contact;
import net.astechdesign.clients.model.contact.ContactRepo;
import net.astechdesign.clients.model.contact.Telephone;
import net.astechdesign.clients.model.product.Product;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ContactsController implements Initializable {

    public AnchorPane productPane;

    @FXML
    private TableView contactsTable;

    @FXML
    private TextField name;

    @FXML
    private TextField address;

    @FXML
    private TextField postcode;

    @FXML
    private TextField telephone;

    @FXML
    private TableColumn nameCol;

    @FXML
    private TableColumn addressCol;

    @FXML
    private TableColumn telCol;

    @FXML
    private TableColumn delCol;

    @FXML
    private AnchorPane confirmDialog;

    @FXML
    private Label dialogName;

    public MainController mainController;

    private Contact deleteContact;

    @FXML
    void addContact(ActionEvent event) {
        Contact contact = new Contact(0, name.getText(), address.getText(), postcode.getText(), new Telephone(telephone.getText()));
        try {
            ContactRepo.save(contact);
            updateContactsTable();
            address.clear();
            postcode.clear();
            telephone.clear();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateContactsTable() {
        try {
            List<Contact> contactsList = ContactRepo.get();
            contactsTable.setItems(FXCollections.observableArrayList(contactsList));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void findContact(ActionEvent event) {
    }

    @FXML
    void confirmDelete(ActionEvent event) {
        try {
            ContactRepo.delete(deleteContact.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateContactsTable();
        confirmDialog.setVisible(false);
    }

    @FXML
    void cancelDelete(ActionEvent event) {
        confirmDialog.setVisible(false);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirmDialog.setVisible(false);
        updateContactsTable();
        nameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("address"));
        telCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("telephone"));

        contactsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                int id = ((Contact) newValue).getId();
                mainController.showDetails(id);
            }
        });
        delCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, Boolean>, ObservableValue<Boolean>>() {
            @Override public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Product, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });
        delCol.setCellFactory(new Callback<TableColumn<Contact, Boolean>, TableCell<Contact, Boolean>>() {
            @Override
            public TableCell<Contact, Boolean> call(TableColumn<Contact, Boolean> param) {
                return new ButtonCell();
            }
        });
    }

    private class ButtonCell extends TableCell<Contact, Boolean> {
        final Button cellButton = new Button("X");

        ButtonCell() {
            cellButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    deleteContact = (Contact)contactsTable.getItems().get(getIndex());
                    dialogName.setText(deleteContact.getName());
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
