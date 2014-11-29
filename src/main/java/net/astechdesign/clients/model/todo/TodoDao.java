package net.astechdesign.clients.model.todo;

import net.astechdesign.clients.repo.Dao;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TodoDao extends Dao<Todo> {

    public TodoDao(DataSource dataSource) {
        super(dataSource);
    }

    public List<Todo> get() throws SQLException {
        return get(LocalDate.now());
    }

    public List<Todo> get(LocalDate date) throws SQLException {
        String sql = "SELECT * FROM (" +
                "SELECT t.id,t.contactId,t.date,t.notes,c.name FROM todos as t, contacts as c where t.contactId=c.id and t.date >= ? " +
                " union SELECT id,-1 as contactId,date,notes,'' as name FROM todos where contactId=-1 and date >= ? )" +
                " order by date";
        return listQuery(sql, Todo.class, convert(date), convert(date));
    }

    private Date convert(LocalDate date) {
        Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public List<Todo> get(int id) throws SQLException {
        String sql = "SELECT id,contactId,date,notes FROM todos where contactId=? order by date";
        return listQuery(sql, Todo.class, id);
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
        LocalDate date = rs.getDate("date").toLocalDate();
        String notes = rs.getString("notes");
        String name = "";
        try {
            name = rs.getString("name");
        } catch (Exception e) {}
        return (T)new Todo(id, contactId, date, notes, name);
    }
}
