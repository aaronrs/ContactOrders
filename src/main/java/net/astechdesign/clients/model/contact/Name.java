package net.astechdesign.clients.model.contact;

public class Name {

    public static final Name DEFAULT = new Name("", "");
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
