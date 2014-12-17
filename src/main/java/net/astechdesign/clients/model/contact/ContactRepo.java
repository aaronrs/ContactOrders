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

    public static List<Contact> find(String text) throws SQLException {
        return instance.findContacts(text);
    }

    public static void save(Contact contact) throws SQLException {
        instance.saveContact(contact);
    }

    public static void update(Contact contact) throws SQLException {
        instance.updateContact(contact);
    }

    public static void delete(int id) throws SQLException {
        instance.deleteContact(id);
    }

    public ContactRepo(DataSource dataSource) {
        this.dataSource = dataSource;
        instance = this;
    }

    private void saveContact(Contact contact) throws SQLException {
        new ContactDao(dataSource).save(contact);
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

    private List<Contact> findContacts(String text) throws SQLException {
        ContactDao contactDao = new ContactDao(dataSource);
        return contactDao.find(text);
    }

    private void deleteContact(int id) throws SQLException {
        ContactDao contactDao = new ContactDao(dataSource);
        contactDao.delete(id);
    }
}
