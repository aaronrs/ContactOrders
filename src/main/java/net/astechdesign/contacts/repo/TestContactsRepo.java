package net.astechdesign.contacts.repo;

import com.google.common.io.CharStreams;
import net.astechdesign.contacts.model.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

public class TestContactsRepo {

    private static boolean initialised = false;
    private final DataSource datasource;

    public TestContactsRepo(DataSource datasource) {
        this.datasource = datasource;
    }

    public void init() throws SQLException {
        if (initialised) return;
        for (String row: readData("categories.csv")) {
            createCategory(row.split(","));
        }
        for (String row: readData("products.csv")) {
            createProduct(row.split(","));
        }
        for (String row: readData("contacts.csv")) {
            createContact(row.split(","));
        }
        for (String row: readData("orders.csv")) {
            createOrder(row.split(","));
        }
        initialised = true;
    }

    private void createCategory(String[] data) throws SQLException {
        Category category = new Category(Integer.parseInt(data[0]), data[1]);
        new CategoriesDao(datasource).save(category);
    }

    private void createProduct(String[] data) throws SQLException {
        Product product = new Product(Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2], data[3]);
        new ProductsDao(datasource).save(product);
    }

    private void createContact(String[] data) throws SQLException {
        Name name = new Name(data[1], data[2]);
        Address address = new Address(Integer.parseInt(data[3]), data[4], data[5], data[6], data[7], data[8], data[9]);
        Contact contact = new Contact(Integer.parseInt(data[0]), name, address);
        ContactsDao contactsDao = new ContactsDao(datasource);
        contactsDao.save(contact);
        System.out.println("");
    }

    private void createOrder(String[] data) throws SQLException {
        Order order = new Order(Integer.parseInt(data[0]),
                Integer.parseInt(data[1]),
                Integer.parseInt(data[2]),
                Integer.parseInt(data[3]),
                Integer.parseInt(data[4]),
                Integer.parseInt(data[5]), null, null, null);
        OrdersDao ordersDao = new OrdersDao(datasource);
        ordersDao.save(Integer.parseInt(data[0]), order);
    }

    private List<String> readData(String fileName) {
        InputStream contactsInputStream = TestContactsRepo.class.getClassLoader().getResourceAsStream(fileName);
        List<String> rows;
        try {
            rows = CharStreams.readLines(new InputStreamReader(contactsInputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rows;
    }
}
