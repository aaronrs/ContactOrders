package integration;

import net.astechdesign.clients.model.contact.Contact;
import net.astechdesign.clients.model.contact.Telephone;
import net.astechdesign.clients.model.order.Order;
import net.astechdesign.clients.model.order.OrderRepo;
import net.astechdesign.clients.model.product.Product;
import net.astechdesign.clients.model.contact.ContactRepo;
import net.astechdesign.clients.model.product.ProductRepo;
import net.astechdesign.clients.repo.DBBuilder;
import net.astechdesign.clients.resources.Contacts;
import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CreateContact {

    private JDBCDataSource dataSource;

    @Before
    public void setUp() throws Exception {
        String DB_URL = "jdbc:hsqldb:mem:Contacts";
        String USER = "SA";
        String PASSWORD = "";
        dataSource = new JDBCDataSource();
        dataSource.setUrl(DB_URL);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);
        new ContactRepo(dataSource);
        DBBuilder.initialiseDb(dataSource);
    }

    @Test
    public void createContact() throws Exception {
        Contacts contacts = new Contacts();
        contacts.save(-1, "first", "last", 99, "name", "address1", "town", "county", "postcode", new Telephone("telephone"));
        Contact contact = ContactRepo.get(0);
        assertThat(contact.id, is(0));
        assertThat(contact.name.first, is("first"));
        assertThat(contact.name.last, is("last"));
        assertThat(contact.telephone.number, is("tel"));
    }

    @Test
    public void createContent() throws Exception {
        Contacts contacts = new Contacts();

        contacts.save(-1, "first", "last", 99, "name", "address1", "town", "county", "postcode", new Telephone("telephone"));
        Contact contact = ContactRepo.get(0);

        contacts.addProduct(1234, -1, "prod", "description");
        Product product = ProductRepo.products().get(0);

        contacts.saveOrder(contact.id, product.id, 2014, 2, 3, 5);
        Order order = OrderRepo.orders(contact.id).get(0);

        assertThat(contact.id, is(0));
        assertThat(contact.name.first, is("first"));
        assertThat(product.id, is(1234));
        assertThat(product.name, is("prod"));
        assertThat(order.productId, is(product.id));
        assertThat(order.contactId, is(contact.id));
        assertThat(order.year, is(2014));
        assertThat(order.month, is(2));
        assertThat(order.day, is(3));
        assertThat(order.amount, is(5));
    }
}
