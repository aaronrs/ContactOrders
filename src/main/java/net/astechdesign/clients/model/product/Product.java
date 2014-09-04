package net.astechdesign.clients.model.product;

public class Product {

    public final int id;
    public final int productId;
    public final String name;
    public final String description;

    public Product(int id, int productId, String name, String description) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.description = description;
    }
}
