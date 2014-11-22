package net.astechdesign.clients.model.product;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Product {

    public final IntegerProperty id;
    public final StringProperty code;
    public final StringProperty name;
    public final StringProperty price;

    public Product(int id, String code, String name, String price) {

        this.id  = new SimpleIntegerProperty(this, "id", id);
        this.code = new SimpleStringProperty(this, "code", code);
        this.name = new SimpleStringProperty(this, "name", name);
        this.price = new SimpleStringProperty(this, "price", price);
    }

    public int getId() {
        return id.getValue();
    }

    public String getCode() {
        return code.getValue();
    }

    public String getName() {
        return name.getValue();
    }

    public String getPrice() {
        return price.getValue();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty codeProperty() {
        return code;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty priceProperty() {
        return price;
    }
}
