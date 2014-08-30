package net.astechdesign.clients.repo;

import com.google.common.io.CharStreams;
import net.astechdesign.clients.model.contact.*;
import net.astechdesign.clients.model.order.Order;
import net.astechdesign.clients.model.order.OrderRepo;
import net.astechdesign.clients.model.product.Product;
import net.astechdesign.clients.model.product.ProductRepo;
import net.astechdesign.clients.model.todo.Todo;
import net.astechdesign.clients.model.todo.TodoRepo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TestContactsRepo {

    private static boolean initialised = false;

    public static void init() throws Exception {
        if (initialised) return;
        String delim = "\\|";
        for (String row: readData("contacts")) {
            createContact(row.split(delim));
        }
        for (String row: readData("products")) {
            createProduct(row.split(delim));
        }
        for (String row: readData("orders")) {
            createOrder(row.split(delim));
        }
        for (String row: readData("todos")) {
            createTodo(row.split(delim));
        }
        initialised = true;
    }

    private static void createProduct(String[] data) throws SQLException {
        Product product = new Product(Integer.parseInt(data[0]), data[1], data[2]);
        ProductRepo.save(product);
    }

    private static void createContact(String[] data) throws SQLException {
        Name name = new Name(data[1], data[2]);
        Address address = new Address(Integer.parseInt(data[3]), data[4], data[5], data[6], data[7], data[8]);
        Contact contact = new Contact(Integer.parseInt(data[0]), name, address, new Telephone(data[9]));
        ContactRepo.save(contact);
    }

    private static void createOrder(String[] data) throws SQLException {
        Order order = new Order(Integer.parseInt(data[0]),
                Integer.parseInt(data[5]), Integer.parseInt(data[1]),
                Integer.parseInt(data[2]),
                Integer.parseInt(data[3]),
                Integer.parseInt(data[4]),
                null, null, null);
        OrderRepo.save(Integer.parseInt(data[0]), order);
    }

    private static void createTodo(String[] data) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Todo todo = new Todo(Integer.parseInt(data[0]),
                Integer.parseInt(data[1]),
                new Name(data[2],data[3]),
                sdf.parse(data[4]),
                sdf.parse(data[5]),
                data[6]);
        TodoRepo.save(todo);
    }

    private static List<String> readData(String fileName) {
        InputStream contactsInputStream = TestContactsRepo.class.getResourceAsStream("/db/data/" + fileName);
        List<String> rows;
        try {
            rows = CharStreams.readLines(new InputStreamReader(contactsInputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rows;
    }
}
