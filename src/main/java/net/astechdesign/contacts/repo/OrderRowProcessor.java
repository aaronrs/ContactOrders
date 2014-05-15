package net.astechdesign.contacts.repo;

import net.astechdesign.contacts.model.Order;
import org.apache.commons.dbutils.BasicRowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRowProcessor extends BasicRowProcessor {
    @Override
    public <T> List<T> toBeanList(ResultSet rs, Class<T> type) throws SQLException {
        List<Order> orderList = new ArrayList<>();
        while (rs.next()) {
            orderList.add(toBean(rs, Order.class));
        }
        return (List<T>)orderList;
    }

    @Override
    public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
        return (T)new Order(
                rs.getInt("contactId"),
                rs.getInt("year"),
                rs.getInt("month"),
                rs.getInt("day"),
                rs.getString("reference"),
                rs.getString("category"),
                rs.getString("name"),
                rs.getString("description"));
    }
}
