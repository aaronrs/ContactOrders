package net.astechdesign.clients.model.todo;

import net.astechdesign.clients.repo.Dao;
import org.joda.time.DateTime;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TodoDao extends Dao<Todo> {

    public TodoDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<Todo> get() throws SQLException {
        String sql = "SELECT t.id,t.contactId,t.date,t.notes,c.name FROM todos as t, contacts as c where t.contactId=c.id and t.date > ?";
        return listQuery(sql, Todo.class, new DateTime().withTimeAtStartOfDay().toDate());
    }

    public List<Todo> get(Date date) throws SQLException {
        String sql = "SELECT t.id,t.contactId,t.date,t.notes,c.name FROM todos as t, contacts as c where t.contactId=c.id and t.date > ?";
        return listQuery(sql, Todo.class, date);
    }

    public List<Todo> get(int id) throws SQLException {
        String sql = "SELECT id,contactId,date,notes FROM todos where contactId=" + id;
        return listQuery(sql, Todo.class);
    }

    public void save(Todo todo) throws SQLException {
        Map<String, Object> dataMap = new LinkedHashMap<>();
        dataMap.put("contactId", todo.contactId);
        dataMap.put("date", todo.date);
        dataMap.put("notes", todo.notes);
        save("todos", dataMap);
    }

    @Override
    public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
        int id = rs.getInt("id");
        int contactId = rs.getInt("contactId");
        Date date = rs.getDate("date");
        String notes = rs.getString("notes");
        String name = "";
        try {
            name = rs.getString("name");
        } catch (Exception e) {}
        return (T)new Todo(id, contactId, date, notes, name);
    }
}