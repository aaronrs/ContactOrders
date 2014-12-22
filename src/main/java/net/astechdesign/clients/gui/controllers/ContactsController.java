package net.astechdesign.clients.gui.controllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import net.astechdesign.clients.model.contact.Contact;
import net.astechdesign.clients.model.contact.ContactRepo;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ContactsController extends Controller implements Initializable {

    @FXML
    private TableView<Contact> contactsTable;

    @FXML
    private TableColumn editCol;

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
        String text = searchText.getText();
        if (!event.getCharacter().equals("\b")) {
            text += event.getCharacter();
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
    void editTelCol(TableColumn.CellEditEvent<Contact, String> event) {
//        Contact contact = event.getTableView().getItems().get(event.getTablePosition().getRow());
//        contact.setTelephone(new Telephone(event.getNewValue()));
//        try {
//            ContactRepo.save(contact);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        update();
    }

    @FXML
    void cancelDelete(ActionEvent e) {
        confirmDialog.setVisible(false);
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
        confirmDialog.setVisible(false);
        update();
        nameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("address"));
        townCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("locality"));
        telCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("telephone"));

//        telCol.setCellFactory(TextFieldTableCell.forTableColumn());

        nameCol.setStyle(Css.COL_FONT_SIZE);
        addressCol.setStyle(Css.COL_FONT_SIZE);
        townCol.setStyle(Css.COL_FONT_SIZE);
        telCol.setStyle(Css.COL_FONT_SIZE);

        deleteContactBtn.disableProperty().bind(contactsTable.getSelectionModel().selectedItemProperty().isNull());

        editCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Contact, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Contact, Boolean> features) {
                return new SimpleBooleanProperty(features.getValue() != null);
            }
        });

        editCol.setCellFactory(new Callback<TableColumn<Contact, Boolean>, TableCell<Contact, Boolean>>() {
            @Override
            public TableCell<Contact, Boolean> call(TableColumn<Contact, Boolean> param) {
                EditContactButtonCell editContactButtonCell = new EditContactButtonCell();
                editContactButtonCell.setAlignment(Pos.CENTER);
                return editContactButtonCell;
            }
        });


/*
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
*/
    }


        private class EditContactButtonCell extends TableCell<Contact, Boolean> {
            final Button cellButton = new Button("/");

            EditContactButtonCell() {
                cellButton.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        setContact(contactsTable.getItems().get(getIndex()));
                        mainController.showDetails();
                    }
                });
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && item != null) {
                    cellButton.getStyleClass().add("button1");
                    setGraphic(cellButton);
                }
            }
        }

}
