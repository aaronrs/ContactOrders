package net.astechdesign.clients.model.contact;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class ContactRepo {

    private static ContactRepo instance;
    private final DataSource dataSource;

    public static List<Contact> get() throws SQLException {
        return instance.getContacts();
    }

    public static Contact get(int id) throws SQLException {
        return instance.getContact(id);
    }

    public static Contact find(String name) throws SQLException {
        return instance.findContact(name);
    }

    public static void save(Contact contact) throws SQLException {
        instance.saveContact(contact.id, contact);
    }

    public ContactRepo(DataSource dataSource) {
        this.dataSource = dataSource;
        instance = this;
    }

    private void saveContact(int id, Contact contact) throws SQLException {
        ContactDao contactDao = new ContactDao(dataSource);
        contactDao.save(contact);
    }

    private void updateContact(Contact contact) throws SQLException {
        ContactDao contactDao = new ContactDao(dataSource);
        contactDao.update(contact);
    }

    private Contact getContact(int id) throws SQLException {
        return new ContactDao(dataSource).getContact(id);
    }

    private List<Contact> getContacts() throws SQLException {
        return new ContactDao(dataSource).getContacts();
    }

    public Contact findContact(String name) throws SQLException {
        ContactDao contactDao = new ContactDao(dataSource);
        return contactDao.find(name);
    }
}
