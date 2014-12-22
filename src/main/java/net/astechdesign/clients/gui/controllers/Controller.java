package net.astechdesign.clients.gui.controllers;

import net.astechdesign.clients.model.contact.Contact;
import net.astechdesign.clients.model.contact.ContactRepo;
import net.astechdesign.clients.model.contact.Telephone;

import java.sql.SQLException;

public abstract class Controller {

    private static final Contact CONTACT = new Contact(-1, "", "", "", "", "", "", new Telephone(""));

    private static Contact contact;

    protected MainController mainController;

    public void mainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setContact(Contact val) {
        contact = val;
    }

    public void setContact(int id) {
        if (id == -1) {
            contact = null;
            return;
        }
        try {
            contact = ContactRepo.get(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Contact getContact() {
        if (contact == null) return CONTACT;
        return contact;
    }

    public int getContactId() {
        return getContact().getId();
    }

    public void clearContact() {
        contact = null;
    }

    public abstract void update();
}
