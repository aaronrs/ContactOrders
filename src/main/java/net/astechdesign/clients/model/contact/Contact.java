package net.astechdesign.clients.model.contact;

public class Contact {

    public final int id;
    public final String first;
    public final String last;
    public final String name;
    public final String address;
    public final String postcode;
    public final Telephone telephone;

    public Contact(int id, String first, String last, String address, String postcode, Telephone telephone) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.name = first + " " + last;
        this.address = address;
        this.postcode = postcode;
        this.telephone = telephone;
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
        return id;
    }
}
