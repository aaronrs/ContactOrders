package net.astechdesign.contacts.repo;

import com.google.common.io.CharStreams;
import net.astechdesign.contacts.model.Address;
import net.astechdesign.contacts.model.Contact;
import net.astechdesign.contacts.model.Name;
import net.astechdesign.contacts.model.Order;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang.StringUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

public class TestContactsRepo {

    public static final String[] CONTACT_COLUMNS = {"id", "first","last","number","houseName","address1","town","county","postcode","telephone"};
    public static final String CONTACT_VALUES = "VALUES(?,?,?,?,?,?,?,?,?,?)";
    public static final String[] ORDER_COLUMNS = {"contactId","year","month","day","reference","category","name","description"};
    public static final String ORDER_VALUES = "VALUES(?,?,?,?,?,?,?,?)";


    private static boolean initialised = false;
    private final DataSource datasource;

    public TestContactsRepo(DataSource datasource) {
        this.datasource = datasource;
    }

    public void init() {
        if (initialised) return;
        InputStream contactsInputStream = TestContactsRepo.class.getClassLoader().getResourceAsStream("contacts.csv");
        List<String> rows;
        try {
            rows = CharStreams.readLines(new InputStreamReader(contactsInputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String row: rows) {
            createContact(row.split(","));
        }
        InputStream ordersInputStream = TestContactsRepo.class.getClassLoader().getResourceAsStream("orders.csv");
        try {
            rows = CharStreams.readLines(new InputStreamReader(ordersInputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String row: rows) {
            createOrder(row.split(","));
        }
        initialised = true;
    }

    private void createContact(String[] data) {
        Name name = new Name(data[1], data[2]);
        Address address = new Address(Integer.parseInt(data[3]), data[4], data[5], data[6], data[7], data[8], data[9]);
        Contact contact = new Contact(Integer.parseInt(data[0]), name, address);
        try {
            QueryRunner queryRunner = new QueryRunner(datasource);
                String sql = "INSERT INTO contacts (" +
                        StringUtils.join(CONTACT_COLUMNS, ",") + ") " +
                        CONTACT_VALUES;
                queryRunner.update(sql,
                        contact.id,
                        contact.name.first,
                        contact.name.last,
                        contact.address.number,
                        contact.address.houseName,
                        contact.address.address1,
                        contact.address.town,
                        contact.address.county,
                        contact.address.postcode,
                        contact.address.telephone);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createOrder(String[] data) {
        Order order = new Order(Integer.parseInt(data[0]),
                Integer.parseInt(data[1]),
                Integer.parseInt(data[2]),
                Integer.parseInt(data[3]),
                data[4], data[5], data[6], data[7]);
        try {
            QueryRunner queryRunner = new QueryRunner(datasource);
            String sql = "INSERT INTO orders (" +
                    StringUtils.join(ORDER_COLUMNS, ",") + ") " +
                    ORDER_VALUES;
            queryRunner.update(sql,
                    Integer.parseInt(data[0]),
                    order.year,
                    order.month,
                    order.day,
                    order.reference,
                    order.category,
                    order.name,
                    order.description);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
