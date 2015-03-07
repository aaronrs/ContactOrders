package net.astechdesign.clients.model.order;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class OrderRepo {

    private static OrderRepo instance;
    private final DataSource dataSource;

    public static List<Order> currentOrders() throws SQLException {
        return instance.getCurrentOrders();
    }

    public static List<Order> orders(int contactId) throws SQLException {
        return instance.getOrders(contactId);
    }

    public static List<Order> uniqueOrders() throws SQLException {
        return instance.getUniqueOrders();
    }

    public static void save(Order order) throws SQLException {
        instance.saveOrder(order);
    }

    public static void deleteOrder(Order order) throws SQLException {
        instance.delete(order);
    }

    public OrderRepo(DataSource dataSource) {
        this.dataSource = dataSource;
        instance = this;
    }

    private void saveOrder(Order order) throws SQLException {
        new OrdersDao(dataSource).save(order);
    }

    private List<Order> getOrders(int contactId) throws SQLException {
        return new OrdersDao(dataSource).get(contactId);
    }

    private List<Order> getUniqueOrders() throws SQLException {
        return new OrdersDao(dataSource).getUniqueOrders();
    }

    private List<Order> getCurrentOrders() throws SQLException {
        return new OrdersDao(dataSource).getOrders();
    }

    private void delete(Order order) throws SQLException {
        new OrdersDao(dataSource).delete(order);
    }
}
