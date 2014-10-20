package net.astechdesign.clients.model.product;

import net.astechdesign.clients.repo.Dao;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductDao extends Dao<Product> {

    public ProductDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<Product> get() throws SQLException {
        String sql = "SELECT * FROM products order by name";
        return listQuery(sql, Product.class);
    }

    public Product find(int productId) throws SQLException {
        String sql = "SELECT * FROM products where productId=" + productId;
        return query(sql, Product.class);
    }

    public void save(Product product) throws SQLException {
        Map<String, Object> dataMap = new LinkedHashMap<>();
        dataMap.put("productId", product.productId);
        dataMap.put("name", product.name);
        dataMap.put("description", product.description);
        save("products", dataMap);
    }

    @Override
    public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
        int id = rs.getInt("id");
        int productId = rs.getInt("productId");
        String name = rs.getString("name");
        String description = rs.getString("description");
        return (T)new Product(id, productId, name, description);
    }

    public void delete(int productId) throws SQLException {
        update("delete from products where productId = ?", productId);
    }
}
