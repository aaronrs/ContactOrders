package net.astechdesign.clients.model.order;

import java.time.LocalDate;

public class Order {

    public final String contact;
    public final String product;
    public final LocalDate deliveryDate;
    public final int amount;
    public final LocalDate createDate;

    public Order(String contact, String product, LocalDate deliveryDate, int amount, LocalDate createDate) {
        this.contact = contact;
        this.product = product;
        this.deliveryDate = deliveryDate;
        this.amount = amount;
        this.createDate = createDate;
    }
}
