package net.astechdesign.contacts.repo;

import net.astechdesign.contacts.model.Todo;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodoListDao  extends Dao<Todo>{

    public TodoListDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<Todo> get() throws SQLException {
        String sql = "SELECT id,contactId,start,end,notes FROM todoList";
        return listQuery(sql, Todo.class);
    }

    public List<Todo> get(int contactId) throws SQLException {
        String sql = "SELECT id,contactId,start,end,notes FROM todoList where contactId=?";
        return listQuery(sql, Todo.class, contactId);
    }

    @Override
    public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
        int id = rs.getInt("id");
        int contactId = rs.getInt("contactId");
        Date start = rs.getDate("start");
        Date end = rs.getDate("end");
        String notes = rs.getString("notes");
        return (T)new Todo(id,contactId, start, end, notes);
    }

    public void save(Todo todo) throws SQLException {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("contactId", todo.contactId);
        dataMap.put("start", todo.start);
        dataMap.put("end", todo.end);
        dataMap.put("notes", todo.notes);
        save("todoList", dataMap);
    }
}
