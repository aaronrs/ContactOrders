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

    public static List<Contact> find(String firstName, String lastName, String address, String postcode, Telephone tel) throws SQLException {
        return instance.findContacts(firstName, lastName, address, postcode, tel.number);
    }

    public static void save(Contact contact) throws SQLException {
        instance.saveContact(contact);
    }

    public ContactRepo(DataSource dataSource) {
        this.dataSource = dataSource;
        instance = this;
    }

    private void saveContact(Contact contact) throws SQLException {
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

    public List<Contact> findContacts(String firstName, String lastName, String address, String postcode, String tel) throws SQLException {
        ContactDao contactDao = new ContactDao(dataSource);
        return contactDao.find(firstName, lastName, address, postcode, tel);
    }
}
