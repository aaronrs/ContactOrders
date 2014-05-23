package net.astechdesign.contacts.model;

public class Product {

    public final int id;
    public final int categoryId;
    public final String name;
    public final String description;

    public Product(int id, int categoryId, String name, String description) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }
}
