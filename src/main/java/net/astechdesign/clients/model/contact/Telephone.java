package net.astechdesign.clients.model.contact;

public class Telephone {

    public final String number;

    public Telephone(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number;
    }
}
