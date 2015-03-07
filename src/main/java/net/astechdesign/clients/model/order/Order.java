package net.astechdesign.clients.model.order;

import javafx.beans.property.*;
import net.astechdesign.clients.model.contact.Contact;

import java.time.LocalDate;

public class Order {

    public final IntegerProperty id;
    public final ObjectProperty<Contact> contact;
    public final StringProperty product;
    public final ObjectProperty<LocalDate> deliveryDate;
    public final StringProperty price;
    public final IntegerProperty amount;
    public final ObjectProperty<LocalDate> createDate;

    public Order(int id, Contact contact, String product, LocalDate deliveryDate, String price, int amount, LocalDate createDate) {
        this.id = new SimpleIntegerProperty(id);
        this.contact = new SimpleObjectProperty<>(contact);
        this.product = new SimpleStringProperty(product);
        this.deliveryDate = new SimpleObjectProperty<>(deliveryDate);
        this.price = new SimpleStringProperty(price);
        this.amount = new SimpleIntegerProperty(amount);
        this.createDate = new SimpleObjectProperty<>(createDate);
    }

    public Integer getId() {
        return id.getValue();
    }

    public Contact getContact() {
        return contact.getValue();
    }

    public String getProduct() {
        return product.getValue();
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate.getValue();
    }

    public String getPrice() {
        return price.getValue();
    }

    public Integer getAmount() {
        return amount.getValue();
    }

    public LocalDate getCreateDate() {
        return createDate.getValue();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public ObjectProperty<LocalDate> deliveryDateProperty() {
        return deliveryDate;
    }

    public ObjectProperty<Contact> contactProperty() {
        return contact;
    }

    public StringProperty productProperty() {
        return product;
    }

    public StringProperty priceProperty() {
        return price;
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public ObjectProperty<LocalDate> createDateProperty() {
        return createDate;
    }

    public void setId(Integer value) {
        id.setValue(value);
    }

    public void setContact(Contact value) {
        contact.setValue(value);
    }

    public void setProduct(String value) {
        product.setValue(value);
    }

    public void setDeliveryDate(LocalDate date) {
        deliveryDate.setValue(date);
    }

    public void setPrice(String value) {
        price.setValue(value);
    }

    public void setAmount(Integer value) {
        amount.setValue(value);
    }

    public void setCreateDate(LocalDate date) {
        createDate.setValue(date);
    }
}
