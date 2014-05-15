package net.astechdesign.contacts.repo;

import net.astechdesign.contacts.model.Address;
import net.astechdesign.contacts.model.Contact;
import net.astechdesign.contacts.model.Name;
import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ContactsRepoTest {

    private ContactsRepo contactsRepo;

    @Before
    public void setUp() throws Exception {
//        contactsRepo = ContactsRepo.getInstance();
//        new ContactBuilder().withName("first1", "last1").build();
    }

    @Test
    public void get() throws Exception {
        String DB_URL = "jdbc:hsqldb:mem:Contacts";
        String USER = "SA";
        String PASSWORD = "";
        JDBCDataSource jdbcDataSource = new JDBCDataSource();
        jdbcDataSource.setUrl(DB_URL);
        jdbcDataSource.setUser(USER);
        jdbcDataSource.setPassword(PASSWORD);
        new ContactsRepo(jdbcDataSource);

        DBBuilder.initialiseDb(jdbcDataSource);

        new TestContactsRepo(jdbcDataSource).init();

        Connection connection = jdbcDataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from contacts where contactId=0");
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("contactId") + "-" + resultSet.getString("first"));
        }
        connection.close();

        int count = ContactsRepo.get().size();
        Contact contact = ContactsRepo.get(0);
        Name name = new Name("test", "test");
        Address address = new Address(
                666,
                "test",
                "test",
                "test",
                "test",
                "test",
                "test"
        );
        Contact newContact = new Contact(-1, name, address);
        ContactsRepo.save(newContact);
        System.out.println(ContactsRepo.get(count));
    }
}
