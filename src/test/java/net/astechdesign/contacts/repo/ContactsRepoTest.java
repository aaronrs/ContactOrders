package net.astechdesign.contacts.repo;

import net.astechdesign.contacts.model.Address;
import net.astechdesign.contacts.model.Contact;
import net.astechdesign.contacts.model.Name;
import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ContactsRepoTest {

    private ContactsRepo contactsRepo;

    @Test
    public void get() throws Exception {
        JDBCDataSource dataSource = new JDBCDataSource();
        dataSource.setUrl("jdbc:hsqldb:mem:Contacts");
        dataSource.setUser("SA");
        dataSource.setPassword("");

        DBBuilder.initialiseDb(dataSource);
        new TestContactsRepo(dataSource).init();

        new ContactsRepo(dataSource);

        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from contacts where id=0");
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("contactId") + "-" + resultSet.getString("first"));
        }
        connection.close();

        int count = ContactsRepo.getContacts().size();
        Contact contact = ContactsRepo.getContact(0);
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
        System.out.println(ContactsRepo.getContact(count));
    }
}
