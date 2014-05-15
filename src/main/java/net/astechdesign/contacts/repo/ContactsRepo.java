package net.astechdesign.contacts.repo;

import com.google.common.collect.Sets;
import net.astechdesign.contacts.model.Contact;
import net.astechdesign.contacts.model.Order;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ContactsRepo {

    private static ContactsRepo instance;
    private final DataSource dataSource;

    public static List<Contact> get() throws SQLException {
        return instance.getContacts();
    }

    public static Contact get(int id) throws SQLException {
        return instance.getContact(id);
    }

    public static void save(Contact contact) throws SQLException {
        instance.saveContact(contact.id, contact);
    }

    public static void save(int id, Order order) throws SQLException {
        instance.saveOrder(id, order);
    }

    public static List<Order> orders(int id) throws SQLException {
        return instance.getOrders(id);
    }

    public ContactsRepo(DataSource dataSource) {
        this.dataSource = dataSource;
        instance = this;
    }

    private void saveContact(int id, Contact contact) throws SQLException {
        new ContactsDao(dataSource).save(contact);
    }

    private Contact getContact(int id) throws SQLException {
        return new ContactsDao(dataSource).get(id);
    }

    private List<Contact> getContacts() throws SQLException {
        return new ContactsDao(dataSource).get();
    }

    private List<Order> getOrders(int contactId) throws SQLException {
        return new OrdersDao(dataSource).get(contactId);
    }

    private void saveOrder(int id, Order order) throws SQLException {
        new ContactsDao(dataSource).save(order);
    }
}
