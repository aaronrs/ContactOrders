package net.astechdesign.clients.model;

public class Contact {

    public final int id;
    public final Name name;
    public final Address address;

    public Contact(int id, Name name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
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
