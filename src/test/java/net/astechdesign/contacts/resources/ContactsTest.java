package net.astechdesign.contacts.resources;

import net.astechdesign.contacts.repo.ContactsRepo;
import net.astechdesign.contacts.repo.TestContactsRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ContactsTest {

    String contact1Json = "" +
            "{\"contactId\":0," +
            "\"name\":{\"first\":\"Firstname\",\"last\":\"Surname\"}," +
            "\"address\":{\"number\":99,\"houseName\":\"Housename\",\"address1\":\"AddressLine1\",\"town\":\"Town\",\"county\":\"County\",\"postcode\":\"POST CODE\",\"telephone\":\"01111111111\"}," +
            "\"orders\":[{\"year\":2014,\"month\":5,\"day\":9,\"name\":\"Order Name\"}]}";

    String contact2Json = "" +
            "{\"contactId\":1," +
            "\"name\":{\"first\":\"F\",\"last\":\"L\"}," +
            "\"address\":{\"number\":1,\"houseName\":\"H\",\"address1\":\"A\",\"town\":\"T\",\"county\":\"C\",\"postcode\":\"AA1 1AA\",\"telephone\":\"123\"}}";

    @Mock private ContactsRepo repo;

    private final Contacts contacts = new Contacts();

    @Before
    public void setUp() throws Exception {
        new TestContactsRepo(null).init();
    }

    @Test
    public void get_shouldReturnListOfContacts() throws Exception {
        assertThat(contacts.get(), is("[" + contact1Json + "]"));
    }

    @Test
    public void getContactById_shouldReturnContact_giveAnId() throws Exception {
        assertThat(contacts.getContactById(0), is(contact1Json));
    }

    @Test
    public void save_shouldSaveContact_givenContactDetails() throws Exception {
        contacts.save(-1, "F", "L", 1, "H", "A", "T", "C", "AA1 1AA", "123");
        assertThat(contacts.getContactById(1), is(contact2Json));
    }
}
