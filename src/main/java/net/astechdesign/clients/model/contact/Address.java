package net.astechdesign.clients.model.contact;

public class Address {

    public final int number;
    public final String houseName;
    public final String address1;
    public final String town;
    public final String county;
    public final String postcode;

    public Address(int number, String houseName, String address1, String town, String county, String postcode) {

        this.number = number;
        this.houseName = houseName;
        this.address1 = address1;
        this.town = town;
        this.county = county;
        this.postcode = postcode;
    }

    @Override
    public String toString() {
        return number + " " +
                houseName + " " +
                address1 + ", " +
                town + ", " +
                county + ", " +
                postcode;
    }
}
