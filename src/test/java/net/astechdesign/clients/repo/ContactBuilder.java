package net.astechdesign.clients.repo;

import net.astechdesign.clients.model.contact.Contact;
import net.astechdesign.clients.model.contact.Telephone;

import java.time.LocalDate;

public class ContactBuilder {

    private String name = "Tom Thumb";

    private int number = 1;
    private String house = "house";
    private String addressLine1 = "addressLine1";
    private String town = "town";
    private String county = "county";
    private String postcode = "AB1 2CD";
    private String telephone = "01234567890";

    private LocalDate orderDate = LocalDate.now();
    private String orderName = "Name";
    private String orderDescription = "Description";

    public Contact build() {
        String address = number  + " " + house + " " + addressLine1 + " " + town + " " + county;
        Contact contact = new Contact(0, name, address, county, postcode, new Telephone(telephone));
        return contact;
    }

    public ContactBuilder withName(String name) {
        this.name = name;
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
