package net.astechdesign.clients.model.order;

import java.util.Date;

public class Order {

    public final int contactId;
    public final Date date;
    public final int amount;
    public final int productId;
    public final String name;
    public final String description;

    public Order(int contactId, int productId, Date date, int amount, String name, String description) {
        this.contactId = contactId;
        this.date = date;
        this.amount = amount;
        this.productId = productId;
        this.name = name;
        this.description = description;
    }
}
