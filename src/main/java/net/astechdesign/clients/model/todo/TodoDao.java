package net.astechdesign.clients.model.todo;

import net.astechdesign.clients.model.contact.Name;
import net.astechdesign.clients.repo.Dao;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TodoDao extends Dao<Todo> {

    public TodoDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<Todo> get() throws SQLException {
        String sql = "SELECT t.id,t.contactId,t.start,t.end,t.notes,c.first,c.last FROM todos as t, contacts as c where t.contactId=c.id";
        return listQuery(sql, Todo.class);
    }

    public void save(Todo todo) throws SQLException {
        Map<String, Object> dataMap = new LinkedHashMap<>();
        dataMap.put("id", todo.id);
        dataMap.put("contactId", todo.contactId);
        dataMap.put("start", todo.start);
        dataMap.put("end", todo.end);
        dataMap.put("notes", todo.notes);
        save("todos", dataMap);
    }

    @Override
    public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {
        int id = rs.getInt("id");
        int contactId = rs.getInt("contactId");
        Name contactName = new Name(rs.getString("first"),rs.getString("last"));
        Date start = rs.getDate("start");
        Date end = rs.getDate("end");
        String notes = rs.getString("notes");
        return (T)new Todo(id, contactId, contactName, start, end, notes);
    }
}
