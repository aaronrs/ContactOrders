package net.astechdesign.clients.repo;

import com.google.common.io.CharStreams;
import net.astechdesign.clients.model.contact.Contact;
import net.astechdesign.clients.model.contact.ContactRepo;
import net.astechdesign.clients.model.product.Product;
import net.astechdesign.clients.model.product.ProductRepo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

public class TestContactsRepo {

    private static boolean initialised = false;
    private static String oldProId;

    public static void init() throws Exception {
        if (initialised) return;
        for (String row: readData("products")) {
            createProduct(row.split("\\|"));
        }
        int count = 0;
        for (String row: readData("marius.csv")) {
//            System.out.println(row);
//            FullName,Organisation,Property,Street,Locality,Town,County,Postcode
            if (count > 20) break;
            String[] data = row.split("\\|");
            String fullname = data[0].trim();
            String property = data[2].trim();
            String street = data[3].trim();
            String locality = data[4].trim();
            String town = data[5].trim();
            if (locality.length() == 0) {
                locality = town;
            }
            String county = data[6].trim();
            String postcode = data[7].trim();

            StringBuilder address = new StringBuilder();
            if (property.length() > 0) {
                address.append(property);
            }
            addressAppend(street, address);

            Contact contact = new Contact(0, fullname, address.toString(), locality, town, county, postcode, null);
            List<Contact> contacts = ContactRepo.find(fullname);
            if (!contacts.contains(contact)) {
                ContactRepo.save(contact);
            }
            count++;
        }
//        for (String row: readData("todos")) {
//            createTodo(row.split(delim));
//        }
//        for (String row: readData("orders")) {
//            createOrder(row.split(delim));
//        }
        initialised = true;
    }

    private static void addressAppend(String street, StringBuilder address) {
        if (street.length() > 0) {
            if (address.length() > 0) {
                address.append(", ");
            }
            address.append(street);
        }
    }

    private static void createProduct(String[] data) {
        String name = data[0];
        String code = "";
        for (String val :name.split(" ")) {
            code += val.substring(0,1);
        }
        String price = data[1].substring(1);
        Product product = new Product(0, code, name, price);
        try {
            ProductRepo.save(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//
//    private static void createTodo(String[] data) throws Exception {
//        Todo todo = new Todo(Integer.parseInt(data[0]),
//                Integer.parseInt(data[1]),
//                LocalDate.parse(data[4], DateTimeFormatter.ofPattern("yyyy/MM/dd")),
//                data[6], "", "");
//        TodoRepo.save(todo);
//    }

//    private static void createOrder(String[] data) throws SQLException {
//        // TODO
//    }

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
