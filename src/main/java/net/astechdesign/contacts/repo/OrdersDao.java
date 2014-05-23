package net.astechdesign.contacts.repo;

import net.astechdesign.contacts.model.Order;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersDao extends Dao<Order> {

    public OrdersDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<Order> get(int contactId) throws SQLException {
        String sql = "SELECT contactId,year,month,day,amount,productId,name,description,category " +
                "FROM orders INNER JOIN products ON orders.productId=products.id " +
                "INNER JOIN categories ON products.categoryId=categories.id " +
                "WHERE contactId=?";
        return listQuery(sql, Order.class, contactId);
    }

    public void save(int contactId, Order order) throws SQLException {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("contactId", contactId);
        dataMap.put("year", order.year);
        dataMap.put("month", order.month);
        dataMap.put("day", order.day);
        dataMap.put("productId", order.productId);
        dataMap.put("amount", order.amount);
        save("orders", dataMap);
    }

    @Override
    public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
        int contactId = rs.getInt("contactId");
        int year = rs.getInt("year");
        int month = rs.getInt("month");
        int day = rs.getInt("day");
        int amount = rs.getInt("amount");
        int productId = rs.getInt("productId");
        String name = rs.getString("name");
        String description = rs.getString("description");
        String category = rs.getString("category");
        return (T)new Order(contactId, year, month, day, amount, productId, name, description, category);
    }
}
