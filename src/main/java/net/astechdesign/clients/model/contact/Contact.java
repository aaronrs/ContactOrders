package net.astechdesign.clients.model.contact;

import javafx.beans.property.*;
import net.astechdesign.clients.model.Item;
import org.apache.commons.lang.StringUtils;

public class Contact implements Item {

    public final IntegerProperty id;
    public final StringProperty name;
    public final StringProperty address;
    public final StringProperty postcode;
    public final ObjectProperty<Telephone> telephone;

    public Contact(int id, String name, String address, String postcode, Telephone telephone) {

        this.id  = new SimpleIntegerProperty(this, "id", id);
        this.name = new SimpleStringProperty(this, "name", name);
        this.address = new SimpleStringProperty(this, "address", address);
        this.postcode = new SimpleStringProperty(this, "postcode", postcode);
        this.telephone = new SimpleObjectProperty<>(this, "telephone", telephone);
    }

    public int getId() {
        return id.getValue();
    }

    public String getName() {
        return name.getValue();
    }

    public String getAddress() {
        return address.getValue();
    }

    public String getPostcode() {
        return postcode.getValue();
    }

    public Telephone getTelephone() {
        return telephone.getValue();
    }

    public void setId(int id) {
        this.id.setValue(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setPostcode(String postcode) {
        this.postcode.set(postcode);
    }

    public void setTelephone(Telephone telephone) {
        this.telephone.set(telephone);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty addressProperty() {
        return address;
    }

    public StringProperty postcodeProperty() {
        return postcode;
    }

    public ObjectProperty<Telephone> telephoneProperty() {
        return telephone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (id != contact.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.getValue();
    }

    public boolean incomplete() {
        return StringUtils.isEmpty(getName()) || StringUtils.isEmpty(getAddress());
    }
}
