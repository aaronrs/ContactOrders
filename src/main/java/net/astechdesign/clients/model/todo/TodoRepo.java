package net.astechdesign.clients.model.todo;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class TodoRepo {

    private static TodoRepo instance;
    private final DataSource dataSource;

    public static List<Todo> todos() throws SQLException {
        return instance.getTodos();
    }

    public static void save(Todo todo) throws SQLException {
        instance.saveTodo(todo);
    }

    public TodoRepo(DataSource dataSource) {
        this.dataSource = dataSource;
        instance = this;
    }

    private void saveTodo(Todo todo) throws SQLException {
        new TodoDao(dataSource).save(todo);
    }

    private List<Todo> getTodos() throws SQLException {
        return new TodoDao(dataSource).get();
    }
}
