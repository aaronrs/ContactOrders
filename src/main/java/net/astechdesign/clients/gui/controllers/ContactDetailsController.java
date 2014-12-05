package net.astechdesign.clients.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.astechdesign.clients.model.contact.Contact;
import net.astechdesign.clients.model.contact.ContactRepo;
import net.astechdesign.clients.model.contact.Telephone;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ContactDetailsController extends Controller implements Initializable {

    private Contact contact = new Contact(0, "", "", "", null);

    private Label titleName;

    @FXML
    private TextField name;

    @FXML
    private TextField address;

    @FXML
    private TextField postcode;

    @FXML
    private TextField telephone;

    @FXML
    private Button updateDetailsBtn;

    @FXML
    void updateDetails(ActionEvent event) {
        Contact newContact = new Contact(contact.getId(), contact.getName(), address.getText(), postcode.getText(), new Telephone(telephone.getText()));
        try {
            ContactRepo.update(newContact);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        updateDetailsBtn.disableProperty().bind(
//                Bindings.equal(address.textProperty(), contact.getAddress())
//                .and(Bindings.equal(postcode.textProperty(),contact.getPostcode()))
//                .and(Bindings.equal(telephone.textProperty(),contact.getTelephone().number))
//        );
    }

    public void setContact(Contact contact) {
        if (contact.getId() == -1) {
            name.setVisible(true);
        } else {
            name.setVisible(false);
        }
        this.contact.setId(contact.getId());
        this.contact.setName(contact.getName());
        this.contact.setAddress(contact.getAddress());
        this.contact.setPostcode(contact.getPostcode());
        this.contact.setTelephone(contact.getTelephone());

        address.setText(contact.getAddress());
        postcode.setText(contact.getPostcode());
        if (contact.getTelephone() != null) {
            telephone.setText(contact.getTelephone().number);
        }
    }

    public void setTitleName(Label nameLabel) {
        titleName = nameLabel;
        name.textProperty().bindBidirectional(titleName.textProperty());
    }
}