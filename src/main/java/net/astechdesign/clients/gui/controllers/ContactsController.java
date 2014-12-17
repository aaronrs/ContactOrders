package net.astechdesign.clients.gui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
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
    private TableColumn nameCol;

    @FXML
    private TableColumn addressCol;

    @FXML
    private TableColumn telCol;

    @FXML
    private TextField searchText;

    @FXML
    void searchContacts(KeyEvent event) {
        String text = searchText.getText();
        if (text == null) return;
        try {
            updateContactsTable(ContactRepo.find(text));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void newContact() {
        setContact(-1);
        mainController.showDetails();
    }

    @Override
    public void update() {
        try {
            contactsTable.getSelectionModel().clearSelection();
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
        update();
        nameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("address"));
        telCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("telephone"));

        nameCol.setStyle(Css.COL_FONT_SIZE);
        addressCol.setStyle(Css.COL_FONT_SIZE);
        telCol.setStyle(Css.COL_FONT_SIZE);


        contactsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (contactsTable.getSelectionModel().getSelectedItem() != null) {
                    searchText.setText(null);
                    setContact((Contact) newValue);
                    mainController.showDetails();
                }
            }
        });
    }
}
