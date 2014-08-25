package net.astechdesign.clients.model;

public class Name {

    public final String first;
    public final String last;

    public Name(String first, String last) {

        this.first = first;
        this.last = last;
    }

    @Override
    public String toString() {
        return first + " " + last;
    }
}
