package net.astechdesign.clients.model.contact;

import javafx.beans.property.*;
import net.astechdesign.clients.model.Item;
import org.apache.commons.lang.StringUtils;

public class Contact implements Item {

    public final IntegerProperty id;
    public final StringProperty name;
    public final StringProperty address;
    public final StringProperty locality;
    public final StringProperty town;
    public final StringProperty county;
    public final StringProperty postcode;
    public final ObjectProperty<Telephone> telephone;

    public Contact(int id, String name, String address, String locality, String town, String county, String postcode, Telephone telephone) {

        this.id  = new SimpleIntegerProperty(this, "id", id);
        this.name = new SimpleStringProperty(this, "name", name);
        this.address = new SimpleStringProperty(this, "address", address);
        this.locality = new SimpleStringProperty(this, "locality", locality);
        this.town = new SimpleStringProperty(this, "town", town);
        this.county = new SimpleStringProperty(this, "county", county);
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

    public String getLocality() {
        return locality.getValue();
    }

    public String getTown() {
        return town.getValue();
    }

    public String getCounty() {
        return county.getValue();
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

    public void setLocality(String locality) {
        this.locality.set(locality);
    }

    public void setTown(String town) {
        this.locality.set(town);
    }

    public void setCounty(String value) {
        this.county.set(value);
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

    public StringProperty localityProperty() {
        return locality;
    }

    public StringProperty townProperty() {
        return town;
    }

    public StringProperty countyProperty() {
        return county;
    }

    public StringProperty postcodeProperty() {
        return postcode;
    }

    public ObjectProperty<Telephone> telephoneProperty() {
        return telephone;
    }

    public boolean incomplete() {
        return StringUtils.isEmpty(getName()) || StringUtils.isEmpty(getAddress());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (getName() != null ? !getName().equals(contact.getName()) : contact.getName() != null) return false;
        if (getAddress() != null ? !getAddress().equals(contact.getAddress()) : contact.getAddress() != null) return false;
        if (getLocality() != null ? !getLocality().equals(contact.getLocality()) : contact.getLocality() != null) return false;
        if (getPostcode() != null ? !getPostcode().equals(contact.getPostcode()) : contact.getPostcode() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getLocality() != null ? getLocality().hashCode() : 0);
        result = 31 * result + (getPostcode() != null ? getPostcode().hashCode() : 0);
        return result;
    }
}
