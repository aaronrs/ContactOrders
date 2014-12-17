package net.astechdesign.clients.repo;

import com.google.common.io.CharStreams;
import net.astechdesign.clients.model.contact.Contact;
import net.astechdesign.clients.model.contact.ContactRepo;
import net.astechdesign.clients.model.contact.Telephone;
import net.astechdesign.clients.model.product.Product;
import net.astechdesign.clients.model.product.ProductRepo;
import net.astechdesign.clients.model.todo.Todo;
import net.astechdesign.clients.model.todo.TodoRepo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TestContactsRepo {

    private static boolean initialised = false;
    private static String oldProId;

    public static void init() throws Exception {
        if (initialised) return;
        String delim = "\\|";
        for (String row: readData("products")) {
            createProduct(row.split(delim));
        }
        for (String row: readData("marius.csv")) {
//            System.out.println(row);
            String[] data = row.split(",");
            String address = data[2] + " " + data[3] + " " + data[4] + " " + data[5] + " " + data[7];
            Contact contact = new Contact(0, data[0], address.replace("  ", " ").trim(), data[9], null);
            ContactRepo.save(contact);
//            createContact(row.split(delim));
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
        String name = data[0];
        String code = "";
        for (String val :name.split(" ")) {
            code += val.substring(0,1);
        }
        Product product = new Product(0, code, name, data[1]);
        try {
            ProductRepo.save(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createContact(String[] data) throws SQLException {
        Contact contact = new Contact(0, data[0], data[1], data[2], new Telephone(data[3]));
        ContactRepo.save(contact);
    }

    private static void createTodo(String[] data) throws Exception {
        Todo todo = new Todo(Integer.parseInt(data[0]),
                Integer.parseInt(data[1]),
                LocalDate.parse(data[4], DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                data[6], "");
        TodoRepo.save(todo);
    }

    private static void createOrder(String[] data) throws SQLException {
        // TODO
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
