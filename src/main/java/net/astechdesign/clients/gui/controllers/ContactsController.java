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

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ContactsController extends Controller implements Initializable {

    public AnchorPane productPane;

    @FXML
    private TableView contactsTable;

    @FXML
    private TextField name;

    @FXML
    private TextField address;

    @FXML
    private TableColumn nameCol;

    @FXML
    private TableColumn addressCol;

    @FXML
    private TableColumn telCol;

    @FXML
    void findContact(ActionEvent event) {
        try {
            String name = this.name.getText();
            String address1 = address.getText();
            if (name.trim().length() == 0 && address1.trim().length() == 0) {
                updateContactsTable();
            } else {
                updateContactsTable(ContactRepo.find(name, address1, null, null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void newContact() {
        mainController.newContact();
    }

    private void updateContactsTable() {
        try {
            updateContactsTable(ContactRepo.get());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateContactsTable(List<Contact> contactsList) {
        contactsTable.setItems(FXCollections.observableArrayList(contactsList));
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
