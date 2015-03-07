package net.astechdesign.clients.gui.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.astechdesign.clients.model.contact.Contact;
import net.astechdesign.clients.model.contact.ContactRepo;
import net.astechdesign.clients.model.contact.Telephone;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ContactDetailsController extends Controller implements Initializable {

    @FXML
    private VBox contactDetails;

    @FXML
    private AnchorPane confirmDialog;

    @FXML
    private  Label dialogName;

    private TextField name = new TextField();
    private TextField address = new TextField();
    private TextField locality = new TextField();
    private TextField town = new TextField();
    private TextField county = new TextField();
    private TextField postcode = new TextField();
    private TextField telephone = new TextField();
    private Button updateDetailsBtn = new Button("UPDATE");
    private Button saveDetailsBtn = new Button("SAVE");
    private Button deleteContactBtn = new Button("DELETE");

    @FXML
    void updateDetails(ActionEvent event) {
        Contact contact = getContact();
        Contact newContact = new Contact(contact.getId(), contact.getName(), address.getText(), locality.getText(), town.getText(), county.getText(), postcode.getText(), new Telephone(telephone.getText()));
        try {
            ContactRepo.update(newContact);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteContact(ActionEvent event) {
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
    void cancelDelete(ActionEvent e) {
        confirmDialog.setVisible(false);
    }

    @FXML
    void saveDetails(ActionEvent event) {
        Contact newContact = new Contact(-1, name.getText(), address.getText(), locality.getText(), town.getText(), county.getText(), postcode.getText(), new Telephone(telephone.getText()));
        if (newContact.incomplete()) return;
        try {
            ContactRepo.save(newContact);
            setContact(newContact);
            saveDetailsBtn.setDisable(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        update();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        confirmDialog.setVisible(false);

        name.setPromptText("name");
        name.setPrefWidth(300);
        address.setPromptText("address");
        address.setPrefWidth(500);
        locality.setPromptText("locality");
        locality.setPrefWidth(300);
        town.setPromptText("town");
        town.setPrefWidth(300);
        county.setPromptText("county");
        county.setPrefWidth(200);
        postcode.setPromptText("postcode");
        postcode.setPrefWidth(200);
        telephone.setPromptText("telephone");
        telephone.setPrefWidth(200);
        updateDetailsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateDetails(event);
            }
        });
        saveDetailsBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveDetails(event);
            }
        });
        deleteContactBtn = new Button("DELETE");
        deleteContactBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteContact(event);
            }
        });

        updateDetailsBtn.getStyleClass().add("button1");
        saveDetailsBtn.getStyleClass().add("button1");
        deleteContactBtn.getStyleClass().add("button1");
    }

    @Override
    public void update() {
        contactDetails.getChildren().clear();
        if (getContactId() == -1) {
            setupNewContact();
        } else {
            editContact();
        }
        Contact contact = getContact();
        name.setText(contact.getName());
        address.setText(contact.getAddress());
        locality.setText(contact.getLocality());
        town.setText(contact.getTown());
        county.setText(contact.getCounty());
        postcode.setText(contact.getPostcode());
        if (contact.getTelephone() != null) {
            telephone.setText(contact.getTelephone().number);
        } else {
            telephone.clear();
        }
    }

    private void setupNewContact() {
        saveDetailsBtn.setDisable(false);
        updateDetailsBtn.setVisible(false);
        deleteContactBtn.setVisible(false);
        saveDetailsBtn.setVisible(true);
        contactDetails.getChildren().add(name);
        contactDetails.getChildren().add(address);
        contactDetails.getChildren().add(locality);
        contactDetails.getChildren().add(town);
        contactDetails.getChildren().add(county);
        contactDetails.getChildren().add(postcode);
        contactDetails.getChildren().add(telephone);
        contactDetails.getChildren().add(saveDetailsBtn);
    }

    private void editContact() {
        updateDetailsBtn.setVisible(true);
        deleteContactBtn.setVisible(true);
        saveDetailsBtn.setVisible(false);
        contactDetails.getChildren().add(address);
        contactDetails.getChildren().add(locality);
        contactDetails.getChildren().add(town);
        contactDetails.getChildren().add(county);
        contactDetails.getChildren().add(postcode);
        contactDetails.getChildren().add(telephone);
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        hBox.getChildren().add(updateDetailsBtn);
        hBox.getChildren().add(deleteContactBtn);
        contactDetails.getChildren().add(hBox);
    }
}
