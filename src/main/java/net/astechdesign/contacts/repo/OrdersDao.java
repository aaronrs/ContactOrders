package net.astechdesign.contacts.repo;

import net.astechdesign.contacts.model.Order;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class OrdersDao {
    public static final String[] ORDER_COLUMNS = {"contactId","year","month","day","reference","category","name","description"};
    public static final String ORDER_VALUES = "VALUES(?,?,?,?,?,?,?,?)";

    private final DataSource dataSource;

    public OrdersDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Order> get(int contactId) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        ResultSetHandler<List<Order>> ordersHandler = new BeanListHandler<>(Order.class, new OrderRowProcessor());
        return queryRunner.query("SELECT * FROM orders where contactId=?", ordersHandler, contactId);
    }

    public void save(int contactId, Order order) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(dataSource);
            String sql = "INSERT INTO orders (" +
                    StringUtils.join(ORDER_COLUMNS, ",") + ") " +
                    ORDER_VALUES;
            queryRunner.update(sql,
                    contactId,
                    order.year,
                    order.month,
                    order.day,
                    order.reference,
                    order.category,
                    order.name,
                    order.description);
    }
}
