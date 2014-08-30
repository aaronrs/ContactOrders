package net.astechdesign.clients.model.product;

import net.astechdesign.clients.repo.Dao;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDao extends Dao<Product> {

    public ProductDao(DataSource dataSource) {
        super(dataSource);
    }


    public List<Product> get() throws SQLException {
        String sql = "SELECT id,categoryId,name,description FROM products";
        return listQuery(sql, Product.class);
    }

    public void save(Product product) throws SQLException {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", product.id);
        dataMap.put("name", product.name);
        dataMap.put("description", product.description);
        save("products", dataMap);
    }

    @Override
    public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
        int id = rs.getInt("id");
        int categoryId = rs.getInt("categoryId");
        String name = rs.getString("name");
        String description = rs.getString("description");
        return (T)new Product(id, name, description);
    }
}
