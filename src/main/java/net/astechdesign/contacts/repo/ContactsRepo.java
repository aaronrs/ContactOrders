package net.astechdesign.contacts.repo;

import net.astechdesign.contacts.model.Contact;
import net.astechdesign.contacts.model.Order;
import net.astechdesign.contacts.model.Product;
import net.astechdesign.contacts.model.Todo;

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

    public static List<Todo> getTodoList() throws SQLException {
        return instance.getTodos();
    }

    public static List<Todo> getTodoList(int contactId) throws SQLException {
        return instance.getTodos(contactId);
    }

    public static void save(Todo todo) throws SQLException {
        instance.saveTodo(todo);
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

    public static void save(Category category) throws SQLException {
        instance.saveCategory(category);
    }

    public ContactsRepo(DataSource dataSource) {
        this.dataSource = dataSource;
        instance = this;
    }

    private void saveContact(int id, Contact contact) throws SQLException {
        ContactsDao contactsDao = new ContactsDao(dataSource);
        if (id == -1) {
            contactsDao.save(contact);
        } else {
            contactsDao.update(contact);
        }
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

    private void saveOrder(int id, Order order) throws SQLException {
        new OrdersDao(dataSource).save(id, order);
    }

    private List<Product> getProducts() throws SQLException {
        return new ProductsDao(dataSource).get();
    }

    private void saveProduct(Product product) throws SQLException {
        new ProductsDao(dataSource).save(product);
    }

    private List<Category> getCategories() throws SQLException {
        return new CategoriesDao(dataSource).get();
    }

    public void saveCategory(Category category) throws SQLException {
        new CategoriesDao(dataSource).save(category);
    }

    private List<Todo> getTodos() throws SQLException {
        return new TodoListDao(dataSource).get();
    }

    private List<Todo> getTodos(int contactId) throws SQLException {
        return new TodoListDao(dataSource).get(contactId);
    }

    private void saveTodo(Todo todo) throws SQLException {
        new TodoListDao(dataSource).save(todo);
    }
}
