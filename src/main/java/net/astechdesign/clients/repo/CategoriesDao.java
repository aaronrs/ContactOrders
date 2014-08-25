package net.astechdesign.clients.repo;

import org.apache.commons.lang.StringUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoriesDao extends Dao<Category> {
    public static final String[] CATEGORY_COLUMNS = {"category"};
    public static final String CATEGORY_VALUES = "VALUES(?)";

    public CategoriesDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<Category> get() throws SQLException {
        String sql = "SELECT id,category FROM categories";
        return listQuery(sql, Category.class);
    }

    public void save(Category category) throws SQLException {
        String sql = "INSERT INTO categories (" +
                StringUtils.join(CATEGORY_COLUMNS,",") + ") " +
                CATEGORY_VALUES;
        Object[] values = {category.category};
        update(sql, values);
    }

    @Override
    public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
        int id = rs.getInt("id");
        String category = rs.getString("category");
        return (T)new Category(id, category);
    }
}
