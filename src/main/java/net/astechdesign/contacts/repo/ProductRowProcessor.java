package net.astechdesign.contacts.repo;

import net.astechdesign.contacts.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowProcessor extends DaoRowProcessor {

    @Override
    public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
        int id = rs.getInt("id");
        int categoryId = rs.getInt("categoryId");
        String name = rs.getString("name");
        String description = rs.getString("description");
        return (T)new Product(id, categoryId, name, description);
    }
}
