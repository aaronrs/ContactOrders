package net.astechdesign.clients.model.order;

import net.astechdesign.clients.model.contact.ContactRepo;
import net.astechdesign.clients.repo.Dao;
import org.apache.commons.lang.StringUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrdersDao extends Dao<Order> {
    public static final String[] ORDER_COLUMNS = {"contact","product","deliveryDate","amount", "createDate"};

    public OrdersDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<Order> get(int contactId) throws SQLException {
        String name = ContactRepo.get(contactId).getName();
        String sql = "SELECT contact,product,createDate,deliveryDate,amount " +
                "FROM orders where contact = ? " +
                "order by deliveryDate";
        return listQuery(sql, Order.class, name);
    }

    public void save(Order order) throws SQLException {
        String sql = "INSERT INTO orders (" +
                StringUtils.join(ORDER_COLUMNS,",") + ") " +
                "VALUES (?" +
                StringUtils.repeat(",?", ORDER_COLUMNS.length - 1) +
                ")";
        Object[] values = {order.contact,
                order.product,
                order.deliveryDate,
                order.amount,
                order.createDate
        };
        update(sql, values);
    }

    @Override
    public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
        String contact = rs.getString("contact");
        String product = rs.getString("product");
        LocalDate createDate = rs.getDate("createDate").toLocalDate();
        LocalDate deliveryDate = rs.getDate("deliveryDate").toLocalDate();
        int amount = rs.getInt("amount");
        return (T)new Order(contact, product, deliveryDate, amount, createDate);
    }
}
