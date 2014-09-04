package net.astechdesign.clients.model.order;

import net.astechdesign.clients.repo.Dao;
import org.apache.commons.lang.StringUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class OrdersDao extends Dao<Order> {
    public static final String[] ORDER_COLUMNS = {"contactId","productId","date","amount"};

    public OrdersDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<Order> get(int contactId) throws SQLException {
        String sql = "SELECT o.contactId,o.productId,o.date,o.amount,p.name,p.description " +
                "FROM orders as o, products as p where o.productId=p.id " +
                "and contactId=?";
        return listQuery(sql, Order.class, contactId);
    }

    public void save(Order order) throws SQLException {
        String sql = "INSERT INTO orders (" +
                StringUtils.join(ORDER_COLUMNS,",") + ") " +
                "VALUES (?" +
                StringUtils.repeat(",?", ORDER_COLUMNS.length - 1) +
                ")";
        Object[] values = {order.contactId,
                order.productId,
                order.date,
                order.amount
        };
        update(sql, values);
    }

    @Override
    public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
        int contactId = rs.getInt("contactId");
        Date date = new Date(rs.getDate("date").getTime());
        int amount = rs.getInt("amount");
        int productId = rs.getInt("productId");
        String name = rs.getString("name");
        String description = rs.getString("description");
        return (T)new Order(contactId, productId, date, amount, name, description);
    }
}
