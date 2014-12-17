package net.astechdesign.clients.model.order;

import net.astechdesign.clients.model.contact.Contact;
import net.astechdesign.clients.model.contact.ContactRepo;
import net.astechdesign.clients.repo.Dao;
import org.apache.commons.lang.StringUtils;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrdersDao extends Dao<Order> {
    public static final String[] ORDER_COLUMNS = {"contactId","product","deliveryDate","amount", "createDate"};

    public OrdersDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<Order> get(int contactId) throws SQLException {
        String sql = "SELECT id,contactId,product,createDate,deliveryDate,amount " +
                "FROM orders " +
                "where contactId = ? " +
                "order by deliveryDate";
        return listQuery(sql, Order.class, contactId);
    }

    public void save(Order order) throws SQLException {
        String sql = "INSERT INTO orders (" +
                StringUtils.join(ORDER_COLUMNS,",") + ") " +
                "VALUES (?" +
                StringUtils.repeat(",?", ORDER_COLUMNS.length - 1) +
                ")";
        Object[] values = {order.contact.getValue().getId(),
                order.product.getValue(),
                order.deliveryDate.getValue(),
                order.amount.getValue(),
                order.createDate.getValue()
        };
        update(sql, values);
    }

    public void delete(Order order) throws SQLException {
        String sql = "delete from order where id = ?";
        update(sql, order.getId());
    }

    @Override
    public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
        int id = rs.getInt("id");
        int contactId = rs.getInt("contactId");
        String product = rs.getString("product");
        LocalDate createDate = rs.getDate("createDate").toLocalDate();
        LocalDate deliveryDate = rs.getDate("deliveryDate").toLocalDate();
        int amount = rs.getInt("amount");
        Contact contact = ContactRepo.get(contactId);
        return (T)new Order(id, contact, product, deliveryDate, amount, createDate);
    }
}
