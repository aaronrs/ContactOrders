package net.astechdesign.clients.repo;

import com.google.common.collect.Lists;
import net.astechdesign.clients.model.contact.Address;
import net.astechdesign.clients.model.contact.Contact;
import net.astechdesign.clients.model.contact.Name;
import net.astechdesign.clients.model.contact.Telephone;
import net.astechdesign.clients.model.order.Order;
import org.joda.time.DateTime;

import java.util.List;

public class ContactBuilder {

    private String first = "Tom";
    private String last = "Thumb";

    private int number = 1;
    private String house = "house";
    private String addressLine1 = "addressLine1";
    private String town = "town";
    private String county = "county";
    private String postcode = "AB1 2CD";
    private String telephone = "01234567890";

    private DateTime orderDate = new DateTime();
    private String orderName = "Name";
    private String orderDescription = "Description";

    public Contact build() {
        Name name = new Name(first, last);
        Address address = new Address(number, house, addressLine1, town, county, postcode);
        Order order = new Order(0, 1, orderDate.getYear(), orderDate.getMonthOfYear(), orderDate.getDayOfMonth(), 1, orderName, orderDescription, "cat");
        List<Order> orders = Lists.newArrayList(order);
        Contact contact = new Contact(0, name, address, new Telephone(telephone));

        return contact;
    }

    public ContactBuilder withName(String first, String last) {
        this.first = first;
        this.last = last;
        return this;
    }

    public ContactBuilder withAddress(int number, String house, String addressLine1, String town, String county, String postcode, String telephone) {
        this.number = number;
        this.house = house;
        this.addressLine1 = addressLine1;
        this.town = town;
        this.county = county;
        this.postcode = postcode;
        this.telephone = telephone;
        return this;
    }

    public ContactBuilder withOrder(String orderName, String orderDescription) {
        this.orderName = orderName;
        this.orderDescription = orderDescription;
        return this;
    }
}
