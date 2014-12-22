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

    public Product find(int id) throws SQLException {
        String sql = "SELECT * FROM products where id=" + id;
        return query(sql, Product.class);
    }

    public List<Product> find(String text) throws SQLException {
        text = text.toUpperCase();
        String sql = "SELECT * FROM products where code like '%" + text + "%' or name like '%" + text + "%' " + " order by name";
        return listQuery(sql, Product.class);
    }

    public Product findByName(String name) throws SQLException {
        String sql = "SELECT * FROM products where name like '" + name + "' and code like '" + name + "'";
        return query(sql, Product.class);
    }

    public void save(Product product) throws SQLException {
        if (ProductRepo.findByName(product.getName()) == null) {
            Map<String, Object> dataMap = new LinkedHashMap<>();
            dataMap.put("code", product.getCode());
            dataMap.put("name", product.getName());
            dataMap.put("price", product.getPrice());
            save("products", dataMap);
        } else {
            Map<String, Object> keyMap = new LinkedHashMap<>();
            keyMap.put("id", product.getId());
            Map<String, Object> dataMap = new LinkedHashMap<>();
            dataMap.put("code", product.getCode());
            dataMap.put("name", product.getName());
            dataMap.put("price", product.getPrice());
            replace("products", dataMap, keyMap);
        }
    }

    public void delete(int id) throws SQLException {
        update("delete from products where id = ?", id);
    }

    @Override
    public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
        int id = rs.getInt("id");
        String productId = rs.getString("code");
        String name = rs.getString("name");
        String price = rs.getString("price");
        return (T)new Product(id, productId, name, price);
    }
}
