package net.astechdesign.contacts.repo;

import net.astechdesign.contacts.model.Contact;
import net.astechdesign.contacts.model.Order;
import net.astechdesign.contacts.model.Product;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class ContactsRepo {

    private static ContactsRepo instance;
    private final DataSource dataSource;

    public static List<Contact> getContacts() throws SQLException {
        return instance.contacts();
    }

    public static Contact getContact(int id) throws SQLException {
        return instance.contact(id);
    }

    public static void save(Contact contact) throws SQLException {
        instance.saveContact(contact.id, contact);
    }

    public static void save(int id, Order order) throws SQLException {
        instance.saveOrder(id, order);
    }

    public static void save(Product product) throws SQLException {
        instance.saveProduct(product);
    }

    public static List<Order> orders(int id) throws SQLException {
        return instance.getOrders(id);
    }

    public static List<Product> products() throws SQLException {
        return instance.getProducts();
    }

    public static List<Category> categories() throws SQLException {
        return instance.getCategories();
    }

    public ContactsRepo(DataSource dataSource) {
        this.dataSource = dataSource;
        instance = this;
    }

    private void saveContact(int id, Contact contact) throws SQLException {
        new ContactsDao(dataSource).save(contact);
    }

    private Contact contact(int id) throws SQLException {
        return new ContactsDao(dataSource).getContact(id);
    }

    private List<Contact> contacts() throws SQLException {
        return new ContactsDao(dataSource).getContacts();
    }

    private List<Order> getOrders(int contactId) throws SQLException {
        return new OrdersDao(dataSource).get(contactId);
    }

    private List<Product> getProducts() throws SQLException {
        return new ProductsDao(dataSource).get();
    }

    private List<Category> getCategories() throws SQLException {
        return new CategoriesDao(dataSource).get();
    }

    private void saveOrder(int id, Order order) throws SQLException {
        new OrdersDao(dataSource).save(1, order);
    }

    private void saveProduct(Product product) throws SQLException {
        new ProductsDao(dataSource).save(product);
    }
}
