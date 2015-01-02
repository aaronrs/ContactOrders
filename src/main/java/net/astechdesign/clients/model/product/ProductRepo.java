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

    public static void updateCode(Product product) throws SQLException {
        instance.updateProductCode(product);
    }

    public static Product findByName(String name) throws SQLException {
        return instance.findProductByName(name);
    }
    public static Product find(int id) throws SQLException {
        return instance.findProduct(id);
    }

    public static List<Product> find(String text) throws SQLException {
        return instance.findProduct(text);
    }

    public static void delete(int id) throws SQLException {
        instance.deleteProduct(id);
    }

    public ProductRepo(DataSource dataSource) {
        this.dataSource = dataSource;
        instance = this;
    }

    private void saveProduct(Product product) throws SQLException {
        new ProductDao(dataSource).save(product);
    }

    private void updateProductCode(Product product) throws SQLException {
        new ProductDao(dataSource).updateCode(product);
    }

    private List<Product> getProducts() throws SQLException {
        return new ProductDao(dataSource).get();
    }

    private Product findProduct(int id) throws SQLException {
        return new ProductDao(dataSource).find(id);
    }

    private List<Product> findProduct(String text) throws SQLException {
        return new ProductDao(dataSource).find(text);
    }

    private Product findProductByName(String name) throws SQLException {
        return new ProductDao(dataSource).findByName(name);
    }

    private void deleteProduct(int id) throws SQLException {
        new ProductDao(dataSource).delete(id);
    }
}
