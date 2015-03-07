package net.astechdesign.clients.model.product;

import net.astechdesign.clients.repo.QueryRunnerFactory;

import java.sql.SQLException;
import java.util.List;

public class ProductRepo {

    private static ProductRepo instance;
    private final QueryRunnerFactory qrFactory;

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

    public ProductRepo(QueryRunnerFactory qrFactory) {
        this.qrFactory = qrFactory;
        instance = this;
    }

    private void saveProduct(Product product) throws SQLException {
        new ProductDao(qrFactory).save(product);
    }

    private void updateProductCode(Product product) throws SQLException {
        new ProductDao(qrFactory).update(product);
    }

    private List<Product> getProducts() throws SQLException {
        return new ProductDao(qrFactory).get();
    }

    private Product findProduct(int id) throws SQLException {
        return new ProductDao(qrFactory).find(id);
    }

    private List<Product> findProduct(String text) throws SQLException {
        return new ProductDao(qrFactory).find(text);
    }

    private Product findProductByName(String name) throws SQLException {
        return new ProductDao(qrFactory).findByName(name);
    }

    private void deleteProduct(int id) throws SQLException {
        new ProductDao(qrFactory).delete(id);
    }
}
