package net.astechdesign.clients.repo;

import com.google.common.io.CharStreams;
import net.astechdesign.clients.model.contact.*;
import net.astechdesign.clients.model.order.Order;
import net.astechdesign.clients.model.order.OrderRepo;
import net.astechdesign.clients.model.product.Product;
import net.astechdesign.clients.model.product.ProductRepo;
import net.astechdesign.clients.model.todo.Todo;
import net.astechdesign.clients.model.todo.TodoRepo;
import org.joda.time.DateTime;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TestContactsRepo {

    private static boolean initialised = false;
    private static int oldProId;

    public static void init() throws Exception {
        if (initialised) return;
        String delim = "\\|";
        for (String row: readData("products")) {
            createProduct(row.split(delim));
        }
        for (String row: readData("contacts")) {
            createContact(row.split(delim));
        }
        for (String row: readData("todos")) {
//            createTodo(row.split(delim));
        }
        for (String row: readData("orders")) {
//            createOrder(row.split(delim));
        }
        initialised = true;
    }

    private static void createProduct(String[] data) {


        int productId = Integer.parseInt(data[0]);
        if (productId == oldProId) return;
        oldProId = productId;
        Product product = new Product(0, productId, data[2], "");
        try {
            ProductRepo.save(product);
        } catch (SQLException e) {
        }
    }

    private static void createContact(String[] data) throws SQLException {
        String address = data[2] + " " + data[3] + " " + data[4] + " " + data[5] + " " + data[6];
        Contact contact = new Contact(0, data[0] + " " + data[1], address, data[7], new Telephone(data[8]));
        ContactRepo.save(contact);
    }

    private static void createTodo(String[] data) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Todo todo = new Todo(Integer.parseInt(data[0]),
                Integer.parseInt(data[1]),
                sdf.parse(data[4]),
                data[6], "");
        TodoRepo.save(todo);
    }

    private static void createOrder(String[] data) throws SQLException {
        Order order = new Order(Integer.parseInt(data[0]),Integer.parseInt(data[1]),new DateTime().withDate(
                Integer.parseInt(data[5]), Integer.parseInt(data[1]),
                Integer.parseInt(data[4])).toDate(),
                1, null, null, null);
        OrderRepo.save(order);
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
