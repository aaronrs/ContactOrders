package net.astechdesign.clients.model;

public class Order {

    public final int contactId;
    public final int year;
    public final int month;
    public final int day;
    public final int amount;
    public final int productId;
    public final String name;
    public final String description;
    public final String category;

    public Order(int contactId, int productId, int year, int month, int day, int amount, String name, String description, String category) {
        this.contactId = contactId;
        this.year = year;
        this.month = month;
        this.day = day;
        this.amount = amount;
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.category = category;
    }
}
