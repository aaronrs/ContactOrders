package net.astechdesign.clients.gui.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import net.astechdesign.clients.model.contact.Contact;
import net.astechdesign.clients.model.contact.ContactRepo;
import net.astechdesign.clients.model.contact.Telephone;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ContactsController extends Controller implements Initializable {

    @FXML
    private TableView<Contact> contactsTable;

    @FXML
    private TableColumn nameCol;

    @FXML
    private TableColumn addressCol;

    @FXML
    private TableColumn townCol;

    @FXML
    private TableColumn telCol;

    @FXML
    private TextField searchText;

    @FXML
    void searchContacts(KeyEvent event) {
        String text = searchText.getText().toUpperCase();
        if (!event.getCharacter().equals("\b")) {
            text += event.getCharacter().toUpperCase();
        }
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

    @FXML
    private Button deleteContactBtn;

    @FXML
    private Button editContactBtn;

    @FXML
    private AnchorPane confirmDialog;

    @FXML
    private Label dialogName;

    @FXML
    void deleteContact() {
        setContact(-1);
        mainController.showDetails();
    }

    @FXML
    void deleteContact(ActionEvent event) {
        setContact(contactsTable.getSelectionModel().getSelectedItem());
        dialogName.setText(getContact().getName());
        confirmDialog.setVisible(true);
    }

    @FXML
    void confirmDelete(ActionEvent e) {
        try {
            ContactRepo.delete(getContactId());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        confirmDialog.setVisible(false);
        mainController.selectContacts(null);
    }

    @FXML
    void editTelCol(TableColumn.CellEditEvent<Contact, Telephone> event) {
        Contact contact = event.getTableView().getItems().get(event.getTablePosition().getRow());
        contact.setTelephone(event.getNewValue());
        try {
            ContactRepo.update(contact);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        update();
    }

    @FXML
    void cancelDelete(ActionEvent e) {
        confirmDialog.setVisible(false);
    }

    @Override
    public void update() {
        searchText.clear();
        try {
            contactsTable.getSelectionModel().clearSelection();
            updateContactsTable(ContactRepo.get());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateContactsTable(List<Contact> contactsList) {
        ObservableList<Contact> items = contactsTable.getItems();
        items.clear();
        items.addAll(contactsList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirmDialog.setVisible(false);
        update();
        nameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("address"));
        townCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("locality"));
        telCol.setCellValueFactory(new PropertyValueFactory<Contact, Telephone>("telephone"));

        telCol.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Telephone>() {
            @Override
            public String toString(Telephone object) {
                if (object == null) return "";
                return object.number;
            }

            @Override
            public Telephone fromString(String string) {
                return new Telephone(string);
            }
        }));

        nameCol.setStyle(Css.COL_FONT_SIZE);
        addressCol.setStyle(Css.COL_FONT_SIZE);
        townCol.setStyle(Css.COL_FONT_SIZE);
        telCol.setStyle(Css.COL_FONT_SIZE);

        contactsTable.setRowFactory( tv -> {
            TableRow<Contact> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    setContact(row.getItem().getId());
                    mainController.showDetails();
                }
            });
            return row;
        });

        deleteContactBtn.disableProperty().bind(contactsTable.getSelectionModel().selectedItemProperty().isNull());
    }

}
