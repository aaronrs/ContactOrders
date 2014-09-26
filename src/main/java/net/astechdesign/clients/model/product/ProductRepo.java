package net.astechdesign.clients.model.product;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class ProductRepo {

    private static ProductRepo instance;
    private final DataSource dataSource;

    public static List<Product> products() throws SQLException {
        return instance.getProducts();
    }

    public static void save(Product product) throws SQLException {
        instance.saveProduct(product);
    }

    public static void delete(int productId) throws SQLException {
        instance.deleteProduct(productId);
    }

    public ProductRepo(DataSource dataSource) {
        this.dataSource = dataSource;
        instance = this;
    }

    private void saveProduct(Product product) throws SQLException {
        new ProductDao(dataSource).save(product);
    }

    private List<Product> getProducts() throws SQLException {
        return new ProductDao(dataSource).get();
    }

    public void deleteProduct(int productId) throws SQLException {
        new ProductDao(dataSource).delete(productId);
    }
}
