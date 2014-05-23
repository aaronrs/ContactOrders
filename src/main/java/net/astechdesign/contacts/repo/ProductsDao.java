package net.astechdesign.contacts.repo;

import net.astechdesign.contacts.model.Product;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductsDao extends Dao<Product> {

    public ProductsDao(DataSource dataSource) {
        super(dataSource);
    }


    public List<Product> get() throws SQLException {
        String sql = "SELECT id,categoryId,name,description FROM products";
        return listQuery(sql, Product.class);
    }

    public void save(Product product) throws SQLException {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", product.id);
        dataMap.put("categoryId", product.categoryId);
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
        return (T)new Product(id, categoryId, name, description);
    }
}
