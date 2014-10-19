package net.astechdesign.clients.model.order;

import java.util.Date;

public class Order {

    public final int contactId;
    public final Date deliveryDate;
    public final int amount;
    public final int productId;
    public final String name;
    public final String description;
    public final Date createDate;

    public Order(int contactId, int productId, Date deliveryDate, int amount, String name, String description, Date createDate) {
        this.contactId = contactId;
        this.productId = productId;
        this.deliveryDate = deliveryDate;
        this.amount = amount;
        this.name = name;
        this.description = description;
        this.createDate = createDate;
    }
}
