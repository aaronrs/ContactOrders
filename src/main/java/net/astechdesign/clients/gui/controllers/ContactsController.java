package net.astechdesign.clients.gui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import net.astechdesign.clients.model.contact.Contact;
import net.astechdesign.clients.model.contact.ContactRepo;
import net.astechdesign.clients.model.contact.Telephone;

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

    public MainController mainController;

    @FXML
    void addContact(ActionEvent event) {
        Contact contact = new Contact(0, name.getText(), address.getText(), postcode.getText(), new Telephone(telephone.getText()));
        try {
            ContactRepo.save(contact);
            updateContactsTable();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    }

}
