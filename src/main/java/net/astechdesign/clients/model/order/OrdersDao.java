package net.astechdesign.clients.model.order;

import net.astechdesign.clients.repo.Dao;
import org.apache.commons.lang.StringUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrdersDao extends Dao<Order> {
    public static final String[] ORDER_COLUMNS = {"contactId,productId,year,month,day,amount"};
    public static final String ORDER_VALUES = "VALUES(?,?,?,?,?,?)";

    public OrdersDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<Order> get(int contactId) throws SQLException {
        String sql = "SELECT contactId,productId,year,month,day,amount,name,description,category " +
                "FROM orders INNER JOIN products ON orders.productId=products.id " +
                "INNER JOIN categories ON products.categoryId=categories.id " +
                "WHERE contactId=?";
        return listQuery(sql, Order.class, contactId);
    }

    public void save(int contactId, Order order) throws SQLException {
        String sql = "INSERT INTO orders (" +
                StringUtils.join(ORDER_COLUMNS, ",") + ") " +
                ORDER_VALUES;
        Object[] values = {contactId,
                order.productId,
                order.year,
                order.month,
                order.day,
                order.amount
        };
        update(sql, values);
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
        return (T)new Order(contactId, productId, year, month, day, amount, name, description, category);
    }
}
