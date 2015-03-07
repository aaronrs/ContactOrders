package net.astechdesign.clients.model.todo;

import net.astechdesign.clients.model.order.Order;
import net.astechdesign.clients.model.order.OrderRepo;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TodoRepo {

    private static TodoRepo instance;
    private final DataSource dataSource;

    public TodoRepo(DataSource dataSource) {
        this.dataSource = dataSource;
        instance = this;
    }

    public static List<Todo> todos() throws SQLException {
        List<Todo> todos = instance.getTodos();
        for (Todo todo : todos) {
            if (todo.getNotes().startsWith("Delivery")) {
                instance.delete(todo);
            }
        }
        return todos;
    }

    public static List<Todo> deliveries() throws SQLException {
        List<Todo> todos = new ArrayList<>();
        List<Order> orders = OrderRepo.uniqueOrders();
        for (Order order : orders) {
            if (!order.getCreateDate().isEqual(order.getDeliveryDate())) {
                Todo todo = new Todo(order.getId(), order.getContact().getId(), order.getDeliveryDate(), order.getContact().getAddress() + ", " + order.getContact().getTown() + ", " + order.getContact().getPostcode(), order.getContact().getName(), order.getContact().getTelephone().number);
                todos.add(todo);
            }
        }
        return todos;
    }

    public static List<Todo> todos(int id) throws SQLException {
        return instance.getTodos(id);
    }

    public static void save(Todo todo) throws SQLException {
        instance.saveTodo(todo);
    }

    public static void add(Order order) throws SQLException {
        Todo todo = new Todo(-1, order.getContact().getId(), order.getDeliveryDate(), "Delivery:" + order.getProduct(), "", "");
        instance.saveTodo(todo);
    }

    private void saveTodo(Todo todo) throws SQLException {
        new TodoDao(dataSource).save(todo);
    }

    private List<Todo> getTodos() throws SQLException {
        return new TodoDao(dataSource).get();
    }

    private List<Todo> getTodos(int id) throws SQLException {
        return new TodoDao(dataSource).get(id);
    }

    private void delete(Todo todo) throws SQLException {
        new TodoDao(dataSource).delete(todo.getId());
    }
}
