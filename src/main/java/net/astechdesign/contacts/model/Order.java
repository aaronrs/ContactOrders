package net.astechdesign.contacts.model;

public class Order {

    public final int contactId;
    public final int year;
    public final int month;
    public final int day;
    public final String reference;
    public final String category;
    public final String name;
    public final String description;

    public Order(int contactId, int year, int month, int day, String reference, String category, String name, String description) {
        this.contactId = contactId;
        this.year = year;
        this.month = month;
        this.day = day;
        this.reference = reference;
        this.category = category;
        this.name = name;
        this.description = description;
    }
}
