package net.astechdesign.contacts.repo;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriesDao extends Dao<Category> {

    public CategoriesDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<Category> get() throws SQLException {
        String sql = "SELECT id,category FROM categories";
        return listQuery(sql, Category.class);
    }

    public void save(Category category) throws SQLException {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id", category.id);
        dataMap.put("category", category.category);
        save("categories", dataMap);
    }


    @Override
    public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
        int id = rs.getInt("id");
        String category = rs.getString("category");
        return (T)new Category(id, category);
    }

}
